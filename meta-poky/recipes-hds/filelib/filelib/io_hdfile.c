////////////////////////////
// file : io_hdfile.c    ///
// date : 04/04/03		 ///
////////////////////////////

// DBITE File API for Windows Operating System
// Adaptation of Pascal Mathis File API for Linux
// Created by Nelly Said and Maged Jabbour
// Summer - Fall 2002


// Update:
//			April, 4th 2003: Version 2 of the API
//							 Corrected and updated Version 1 by Gery Brissot
//							 Add Verify Function and Force the header to be written


// Next Verion Attendees: Released on the same day
//							One Library For C and C++ P-roject

// Update:
//			May, 23th 2003: Version 2.1 of the API
//							 Corrected and updated Version 2 by Gery Brissot
//							 Add Read Bacward Function


#include <stdlib.h>

#include "ioctl.h"
#include "io_hdfile.h"
#include "err.h"

extern int road_err;

#define TIME_DATA_SIZE 	(sizeof(road_time_t) + sizeof(road_timerange_t))
#define DATA_SIZE_INFO	(sizeof(char))

/////////////////////////////////////////
// Forward Declaration
////////////////////////////////////////
hdfile_t *verify_hdfile(char *path);

/////////////////////////////////////////
// Auxiliaries functions
////////////////////////////////////////

void print_header(hdfile_header_t *h) {
  printf("Signature : %c %c %c %c \nVersion : %d \nOffset : %d \nData Size : %d\n", h->Signature[0],  h->Signature[1],  h->Signature[2],  h->Signature[3], h->VersionNumber, h->DataOffset, h->DataSize );
  printf("Data Type : %d\n", h->Type);
//  printf("Time Min : %I64d\n", h->TimeMin);
//  printf("Time Max : %I64d\n", h->TimeMax);
  printf("Time Min : %lld\n", h->TimeMin);
  printf("Time Max : %lld\n", h->TimeMax);
  printf("Time duration (seconds) : %lld\n", (h->TimeMax-h->TimeMin)/1000000);
  printf("File size : %d \n", h->FileSize);
  printf("Nb record : %d\n", h->NbRecords);
}

static hdfile_header_t *get_hdfile_header(hdfile_t *f) {
	int p,  //la variable p est utilisee pour conserver la position du pointeur dans le fichier
	    nb_read;
	hdfile_header_t *h;

	if (f->h.FileSize < sizeof(hdfile_header_t)) { return NULL; }
	//p = fseek(f->desc, 0, SEEK_CUR);
	p = ftell(f->desc);
	h = (hdfile_header_t *)malloc(sizeof(hdfile_header_t));

	fseek(f->desc, 0, SEEK_SET);
	nb_read = fread(h, sizeof(hdfile_header_t),1,f->desc);
	fseek(f->desc, p, SEEK_SET);

	if (nb_read < 0) { free (h); return NULL; }
	return h;
}

static int set_hdfile_header(hdfile_t *f, hdfile_header_t *h) {
	int p;

	//p = fseek(f->desc, 0, SEEK_CUR);
	p = ftell(f->desc);
	fseek(f->desc, 0, SEEK_SET);
	fwrite(h, sizeof(hdfile_header_t),1,f->desc);
	fseek(f->desc, p, SEEK_SET);

	return DBITE_SUCCESS;
}





// Function for initializing DBT file
hdfile_t * inithdFile(char *nom, int type, int size)
{
  char isfileexist=0;
  hdfile_t *f;
/*
  if (test_hdfile(nom) == 0)
    isfileexist = 1;
  else
    isfileexist = 0;
*/
  if (isfileexist == 0)
  {
    if(create_hdfile(nom) )
    {
      printf("unable to create file %s\n", nom);
      return NULL;
    }
  }
  if (!(f = open_hdfile(nom,WRITE_MODE)))
  {
    road_perror("Opening problem :");
    return NULL;
  }
  set_ioctl_hdfile(f, DATA_TYPE, &type,sizeof(int));
  set_ioctl_hdfile(f, DATA_SIZE, &size,sizeof(int));
  return f;
}

/////////////////////////////////////////
// Create
////////////////////////////////////////

// read/write for user, read for others


//****  creation du fichier  ********


//#define CREATE_MODE 	(S_IRUSR|S_IWUSR| S_IRGRP|S_IROTH) //c est une constante

int create_hdfile(char *path) {
        FILE *desc;
        hdfile_header_t h;

        if ( (desc=fopen(path,"wb+") ) == NULL) {
                return UNABLE_CREAT_FILE; // = la valeur de la fonction de retour = 12
        }

        h.Signature[0] = 'R' ;   h.Signature[1] = 'O' ;   h.Signature[2] = 'A' ;  h.Signature[3] = 'D' ; // = ROAD

	h.Type = FILE_TEXT; // = 2
	h.VersionNumber = VERSION_NUMBER; // a priori c est 0 et doit le rester
        h.DataOffset = sizeof(hdfile_header_t);// la structure a une taille fixe
        h.DataSize = -1;// record liste de time - size - data - size
	h.FileSize = sizeof(hdfile_header_t);//creation donc le size initial est celui du header
	h.TimeMin = h.TimeMax = (road_time_t)-1;// -1 code sur 64 bits
	h.NbRecords = 0; //creation

       if (fwrite(&h,sizeof(hdfile_header_t),1,desc) != 1) {

//				_unlink(path); // le unlink necessite un "_" , unlink efface le fichier
                return HDFILE_WRITE_ERROR;  // = la valeur de la fonction de retour = 10
        }
	fclose(desc);
        return DBITE_SUCCESS; // cte success=0
}






/////////////////////////////////////////
// Open a hdfile
////////////////////////////////////////
hdfile_t *open_hdfile(char *path,int mode)
{
	hdfile_t *f;

	if((f=verify_hdfile(path))==NULL)
		{
			//road_perror("toto");
			printf("Error while probing file integrity\n");
			return(NULL);
		}

	switch (mode)
		{
			case WRITE_MODE : fseek(f->desc, 0, SEEK_END); break;
        	default : fseek(f->desc, f->h.DataOffset, SEEK_SET);
		}
   return f;
}



/////////////////////////////////////////
// Verify a hdfile
////////////////////////////////////////

hdfile_t *verify_hdfile(char *path)
{

	hdfile_t *f;
    FILE *desc;
    hdfile_header_t h;
	char *data;
	road_time_t time_cur;
	road_timerange_t tr_cur;
	int data_size;
	int NbRecords=0;
	int FileSize=0;


        //printf("chemin = %s\n",path);
	if ((desc=fopen(path, "rb+")) == NULL)
		{
			road_err = UNABLE_OPEN_FILE;
			return NULL;
		}

	if	( fread(&h, sizeof(hdfile_header_t),1,desc)  != 1)
		{
			fclose(desc);
			road_err = HDFILE_READ_ERROR; //9
			return NULL;
		}

	if (!(h.Signature[0] == 'R' &&	h.Signature[1] == 'O' &&  h.Signature[2] == 'A' &&	h.Signature[3] == 'D'))
		{
			fclose(desc);
			road_err = BAD_MAGIC_NUMBER;//13
			return NULL;
		}

	if ((f = (hdfile_t *)malloc(sizeof(hdfile_t))) == NULL)
		{
			fclose(desc);
			road_err = DYNAMIC_ALLOCATION_PROBLEM;//3
			return NULL;
		}

	//on rend le fichier correct
	//printf("Header avant correction\n");
	//print_header(&h);
	f->desc=desc;
	f->h=h;

	fseek(f->desc,0,SEEK_END);
	if((long)h.FileSize==ftell(f->desc))
		{
			//puts("File seems good");
			return(f);
		}
	else
		{
			puts("Incorrect File Size. This File must be checked");
			fseek(f->desc,f->h.DataOffset,SEEK_SET);
		}

	// Taille pour le buffer de lecture
	// Pour les donn�es de taille varaible (DataSize = -1) on place 255 car la taille est sur un char
	if(h.DataSize!=-1)
		{
			data_size=h.DataSize;
		}
	else
		{
			data_size=256;
		}

	data=(char *) malloc (data_size * sizeof(char));
	if (data==NULL)
		{
			road_err = DYNAMIC_ALLOCATION_PROBLEM;//3
			return(NULL);
		}

	fseek(desc,0,SEEK_END);
	if (ftell(desc)<=(sizeof(hdfile_header_t)))
		{
			//printf("Passe par ici\n");
			return(f);
		}

	fseek(desc,h.DataOffset,SEEK_SET);
	while(read_hdfile(f, data, &time_cur, &tr_cur) > 0)
		{
			NbRecords++;

			if(NbRecords==1)
				{
					h.TimeMin=time_cur;
				}

			h.TimeMax=time_cur;

			FileSize=ftell(desc);
		}

	h.NbRecords=NbRecords;
	h.FileSize=FileSize;

	f->h=h;
	set_hdfile_header(f, &(f->h));
	fflush(f->desc);
	//printf("Header apres correction\n");
	//print_header(&h);
	return(f);

}


/////////////////////////////////////////
// Test
////////////////////////////////////////

int test_hdfile(char *path) {
        FILE *desc;
         hdfile_header_t h;

         if ((desc=fopen(path, "rb+")) == NULL) {
                return UNABLE_OPEN_FILE;//11
        }

         if (fread(&h, sizeof(hdfile_header_t),1,desc) !=  1) {
              return HDFILE_READ_ERROR;//9
        }

        if (!(h.Signature[0] == 'R' &&  h.Signature[1] == 'O' &&  h.Signature[2] == 'A' &&  h.Signature[3] == 'D'))   {
              return NOT_A_ROAD_FILE;//
        }

     	fclose(desc);
		return DBITE_SUCCESS;
 }




/////////////////////////////////////////
// Read
////////////////////////////////////////

int read_hdfile(hdfile_t *f, void *buffer, road_time_t *time, road_timerange_t *tr) {

	int nb_read,
	    amount = 0;
	int data_size;
	char size;

	data_size = f->h.DataSize;//size du record

	// Read time

	nb_read = fread(time,sizeof(road_time_t),1,f->desc); //time of read data

	if(nb_read!=1)
	{
		if(feof(f->desc)  )
		{
			road_err = HDFILE_EOF;
			return nb_read;
		}  //19
		if(ferror(f->desc))
		{
		    road_err = HDFILE_READ_ERROR;
			return nb_read;  //9
		}
	}

	nb_read =fread(tr, sizeof(road_timerange_t),1,f->desc); //accuracy

	if(nb_read!=1)
	{
		if(feof(f->desc)  )
		{
			road_err = HDFILE_EOF;
			return nb_read;
		}  //19
		if(ferror(f->desc))
		{
		    road_err = HDFILE_READ_ERROR;
			return nb_read;  //9
		}
	}

	// Read data

	//size
	if (f->h.DataSize == -1) {   //structure fixe
		fread(&size, sizeof(char),1,f->desc);
		data_size = (int)size;//??????????????
	}

	//data
	nb_read = fread(buffer, data_size,1,f->desc);

	if(nb_read!=1)
	{
		if(feof(f->desc)  )
		{
			road_err = HDFILE_EOF;
			return nb_read;
		}  //19
		if(ferror(f->desc))
		{
		    road_err = HDFILE_READ_ERROR;
			return nb_read;  //9
		}
	}

	//size
	if (f->h.DataSize == -1) {
		fread(&size,sizeof(char),1,f->desc);
	}

	//size-data-size

	return nb_read;
}

/////////////////////////////////////////
// Read bacward
////////////////////////////////////////

int read_backward_hdfile(hdfile_t *f, void *buffer, road_time_t *time, road_timerange_t *tr) {

	int data_size;
	char size;
	long pos;
	long file_offset;

	data_size = f->h.DataSize;//size du record

	//size
	if (f->h.DataSize == -1)
		{   //structure fixe
			fseek(f->desc,1,SEEK_CUR);
			fread(&size, sizeof(char),-1,f->desc);
			data_size = (int)size;//??????????????
			fseek(f->desc,-(data_size+sizeof(road_time_t)+sizeof(road_timerange_t)+2*sizeof(char)),SEEK_CUR);
			fseek(f->desc,1,SEEK_CUR);
			fread(&size, sizeof(char),-1,f->desc);
			data_size = (int)size;//??????????????
			fseek(f->desc,-(data_size+sizeof(road_time_t)+sizeof(road_timerange_t)+2*sizeof(char)),SEEK_CUR);

		}
	else
		{
			pos=ftell(f->desc);
			file_offset=pos-(2*(data_size+sizeof(road_time_t)+sizeof(road_timerange_t)));
			if(file_offset>f->h.DataOffset)
				fseek(f->desc,file_offset,SEEK_SET);
			else
				return(0);
		}

	return (read_hdfile(f,buffer,time,tr));
}


/////////////////////////////////////////
// Write
////////////////////////////////////////

int write_hdfile(hdfile_t *f, void *buffer, road_time_t time, road_timerange_t tr, int buffer_data_size) { //////a revoir =-1???????
	int nb_write;
	long previous,next;
	int data_size;
	char size;

	fseek(f->desc,f->h.FileSize,SEEK_SET);
	if (f->h.DataSize == -1) {
		if (buffer_data_size==-1) { road_err = DATA_SIZE_UNKNOWN; return -1; } //23
		else { data_size = buffer_data_size; size = (char)data_size; }
	} else { data_size = f->h.DataSize; }


	// Write time
	previous=ftell(f->desc);
	nb_write =fwrite(&time, sizeof(road_time_t) ,1,f->desc);
	next=ftell(f->desc);
	previous=next;
	if (nb_write == -1)
		{
			road_err = HDFILE_WRITE_ERROR; return -1;//10
		}

	nb_write = fwrite(&tr, sizeof(road_timerange_t),1,f->desc);
	next=ftell(f->desc);
	previous=next;
	if (nb_write == -1)
		{
			road_err = HDFILE_WRITE_ERROR; return -1;//10
		}


	// Write data + if required, data_size before AND after the data

	if (f->h.DataSize == -1)
		{
			fwrite(&size, sizeof(char),1,f->desc);
			next=ftell(f->desc);
			previous=next;
		}

	nb_write =fwrite(buffer, data_size,1,f->desc);
	if (nb_write == -1) {
	    road_err = HDFILE_WRITE_ERROR; return -1;//10
	}
	next=ftell(f->desc);

	previous=next;

	if (f->h.DataSize == -1)
		{
			fwrite(&size, sizeof(char),1,f->desc);
			next=ftell(f->desc);
			previous=next;
		}


	// Update
	f->h.FileSize += data_size + TIME_DATA_SIZE + (f->h.DataSize == -1?2*DATA_SIZE_INFO:0);//si -1 alors on 2 octets en addition pour les sizes
	f->h.NbRecords++;
	if (f->h.TimeMin == -1) { f->h.TimeMin = time; }
	f->h.TimeMax = time;
  	return nb_write;
}


/////////////////////////////////////////
// Closef->h.TimeMax=actualtime();
////////////////////////////////////////

int close_hdfile(hdfile_t *f) {
	set_hdfile_header(f, &(f->h));
	fclose(f->desc);
	//print_header(&(f->h));
	free(f);
  f = NULL;
	return DBITE_SUCCESS;
}





/////////////////////////////////////////
// Ioctl
////////////////////////////////////////

int get_ioctl_hdfile(hdfile_t *f, int request, void *buf, int size) {
        hdfile_header_t *h;
        int p;

        switch (request) {
	      case HEADER : h = get_hdfile_header(f);
	  		           if (!h) {
						   road_err = HDFILE_READ_ERROR;}//9

			           else {
						   *(hdfile_header_t *)buf = *h;}

			break;
          case HEADER_AUX  :  //p = fseek(f->desc, 0, SEEK_CUR);
								p = ftell(f->desc);
                              fseek(f->desc, sizeof(hdfile_header_t), SEEK_SET);
			                  if (fread(buf, size,1,f->desc) == -1) {
					              road_err = HDFILE_READ_ERROR;}//9

				              else {
					               road_err = DBITE_SUCCESS; }
                              fseek(f->desc, p, SEEK_SET);
                              break;
          case HEADER_AUX_SIZE  : *((int *)buf) = f->h.DataOffset - sizeof(hdfile_header_t);
                              break;
	      case DATA_TYPE : *((int *)buf) = f->h.Type;
                            break;
          case DATA_SIZE : *((int *)buf) = f->h.DataSize;
                           break;
         }
        return road_err;
}




int set_ioctl_hdfile(hdfile_t *f, int request, void *buf, int size) {

	int p;

	switch (request) {
          case DATA_SIZE : f->h.DataSize = *((int *)buf);
                           set_hdfile_header(f, &(f->h));
                           break;
          case HEADER_AUX : if (f->h.FileSize != sizeof(hdfile_header_t) || size < 0) {
                                road_err = OPERATION_NOT_ALLOWED;
                                return -1;
                            }
                            p = fseek(f->desc, 0, SEEK_CUR);
                            fseek(f->desc, sizeof(hdfile_header_t), SEEK_SET);
			                fwrite(buf, size,1,f->desc);
                            f->h.DataOffset = sizeof(hdfile_header_t) + size;
							f->h.FileSize = f->h.DataOffset;
                            fseek(f->desc, f->h.DataOffset, SEEK_SET);
                            set_hdfile_header(f, &(f->h));
                            break;
	 case DATA_TYPE : f->h.Type = *((int *)buf);
                      set_hdfile_header(f, &(f->h));
                           break;
        }

	fflush(f->desc);

	return DBITE_SUCCESS;
}
