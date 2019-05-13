#pragma once
#include <windows.h>
#include <stdio.h>

class SyncQueue
{
	int *queue;
	int size;
	int head, tail;
	CRITICAL_SECTION critical;
	HANDLE empty, full;
public:
	SyncQueue(int nSize);
	void insert(int nElement);
	int remove();
	~SyncQueue();
};

