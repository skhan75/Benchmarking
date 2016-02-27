#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>
#include <time.h>
#include <math.h>
#include <string.h>
#include <pthread.h>

void *randomAccess(void *blockSize);
void *sequentialAccess(void* blockSize);

int main(){
	int count = 1, j, k, iterations;
	int blockSize = 0;
	struct timeval lStart, lEnd;
	double totalExec, throughput;

	printf("----------------------------------------------------------------------------+\n");
	printf("                          MEMORY BENCHMARKING                               \n");
	printf("----------------------------------------------------------------------------+\n");


	for(int count=1; count<=2; count++)// (i = 1; i <= 2; i++)
	{
		int array[] = {1,1000,1000000 };
		for(j = 0; j < 3; j++){

			blockSize = array[j];
			//n = pow(1024.0, (double)j);

			if(blockSize > 1000)
				iterations = 1000;
			//if(n > 1024)
			//	m = 2000;
			else
				iterations = 1000000;

			struct timeval start, end;
			pthread_t threads[count];

		
			gettimeofday(&lStart, NULL); //Timer Started for Calculating execution time for Random Accessing memory
			for (k = 0; k < count; k++){
				pthread_create(&threads[k], NULL, randomAccess, &blockSize); // Creating threads for Randomly Accessing memory
			}

			for (k = 0; k < count; k++){
				pthread_join(threads[k], NULL);
			}
			gettimeofday(&lEnd, NULL); //Timer Ended

			//Calculating total Random Access Executions
			totalExec = (1000.0*(lEnd.tv_sec - lStart.tv_sec) + (lEnd.tv_usec - lStart.tv_usec)/1000.0)/iterations;
			throughput = (count * blockSize / (1024.0 * 1024.0)) / (totalExec / 1000.0);
			printf("\nThreads: %d\nOperation: *RANDOM ACCESS* \nBlock Size: %10d bytes \nLatency : %10f ms \nThroughput: %10f MB/sec \n", count, blockSize, totalExec/blockSize, throughput);

			//printf("%d\t\t%s\t\t%10d\t%10fms\t%10fMBps\t\n", i, "Random", n, totalExec/n, throughput);
			
			
			gettimeofday(&lStart, NULL);  //Timer Started for Calculating execution time for Sequentially Accessing memory
			for (k = 0; k < count; k++){
				pthread_create(&threads[k], NULL, sequentialAccess, &blockSize); // Sequential Access
			}
			for (k = 0; k < count; k++){
				pthread_join(threads[k], NULL);
			}
			gettimeofday(&lEnd, NULL);
			
			//Calculating total Sequential Access Executions
			totalExec = (1000.0*(lEnd.tv_sec - lStart.tv_sec) + (lEnd.tv_usec - lStart.tv_usec)/1000.0)/iterations;

			throughput = (count * blockSize / (1024.0 * 1024.0)) / (totalExec/ 1000.0);
			printf("\nThreads: %d\n Operation: *SEQUENTIAL ACCESS* \nBlock Size: %10d bytes \nLatency : %10f ms \nThroughput: %10f MB/sec \n", count, blockSize, totalExec/blockSize, throughput);
			//printf("%d\t\t%s\t%10d\t%10fms\t%10fMBps\t\n", count, "Sequential", n, totalExec/n, throughput);
			//printf("With %d threads, sequential access %10d bytes, the latency is %10f ms and the throughput is %10f MB/sec\n", i, n, exec_t/n, throughput);
		//count++;
		}
	} 
	printf("-----------------------------------------------------------------------------\n");
}
void *randomAccess(void *blockSize){
	int *size = (int *)blockSize;
	int i, j, srcID, destID;

	unsigned long iterations = 1024ul * 1024ul * 2100ul;

	if(*size > 1024){
		i = 2000;
	}
	else{
		i = 1000000;
	}

	char * dest = (char *)malloc(iterations * sizeof(char)); //destination memory
	char * src = (char *)malloc(i * (*size) * sizeof(char)); //source memory
	
	for (j = 0; j < i; j++) {
		destID =  rand() % (iterations - *size - 1); //random index
		srcID = rand() % (i * (*size) - *size - 1); //random index
		memcpy(dest + destID, src + srcID, *size);
	}

	free(dest);
	free(src);

	return 0;
}

void *sequentialAccess(void* blockSize){
	int *size = (int *)blockSize;
	int i, j, k, srcID, destID;

	unsigned long iterations = 1024ul * 1024ul * 2100ul;
	
	if (*size > 1024){
		i = 2000;
	}
	else{ 
		i = 1000000;
	}

	char * dest = (char *)malloc(iterations * sizeof(char)); //destination memory
	char * src = (char *)malloc(i * (*size) * sizeof(char)); //source memory
	
	k = 0;
	for (j = 0; j < i; j++) {
		memcpy(dest + k, src + k, *size) ;
		k += (*size); //memory access index
	}

	free(dest);
	free(src);

	return 0;
}
