#include <fstream>
#include <iostream>
#include "ring_queue.h"
#define MESSAGE_SIZE 20
#define QSIZE 5
char filename[] = "binary.bin";
std::fstream file;


void create_file();
void push_queue(const char* );
char* pop_queue();
void print(std::ostream&);


int main()
{
	create_file();
	push_queue("11111");
	push_queue("22222");
	push_queue("3333333");
	std::cout << "popped: " << pop_queue() << std::endl;
	push_queue("444444444");
	push_queue("5555");
	push_queue("666666");
	std::cout << "popped: " << pop_queue() << std::endl;
	std::cout << "popped: " << pop_queue() << std::endl;
	std::cout << std::endl;
	print(std::cout);
	
	file.close();
	return 0;
}


void create_file()
{	
	int first_element = 0, last_element = 0, current_lenth = 0, max = QSIZE;
	file.open(filename, std::ios::binary | std::ios::in | std::ios::out);
	file.clear();
	file.write(reinterpret_cast<char *>(&first_element), sizeof(int));
	file.write(reinterpret_cast<char *>(&last_element), sizeof(int));
	file.write(reinterpret_cast<char *>(&current_lenth), sizeof(int));
	file.write(reinterpret_cast<char *>(&max), sizeof(int));
}

void push_queue(const char* message)
{
	if (std::strlen(message) > MESSAGE_SIZE)
		return;

	file.seekg(0);
	int first_element, last_element, current_lenth, max;
	
	file.read(reinterpret_cast<char *>(&first_element), sizeof(int));
	file.read(reinterpret_cast<char *>(&last_element), sizeof(int));
	file.read(reinterpret_cast<char *>(&current_lenth), sizeof(int));
	file.read(reinterpret_cast<char *>(&max), sizeof(int));
	
	if (current_lenth == max)
		return;

	
	
	file.seekp(4 * sizeof(int) + (MESSAGE_SIZE) * last_element, std::ios::beg);
	
	file.write(message, (MESSAGE_SIZE));
	
	last_element++;
	current_lenth++;
	if (last_element > max - 1)
		last_element = 0;

	

	file.seekp(sizeof(int), std::ios::beg);
	file.write(reinterpret_cast<char *>(&last_element), sizeof(int));
	file.write(reinterpret_cast<char *>(&current_lenth), sizeof(int));
	
}
char* pop_queue()
{
	int first_element, current_lenth, max;
	
	file.seekg(0, std::ios::beg);
	file.read(reinterpret_cast<char *>(&first_element), sizeof(int));
	
	file.seekg(2 * sizeof(int), std::ios::beg);
	file.read(reinterpret_cast<char *>(&current_lenth), sizeof(int));
	file.read(reinterpret_cast<char *>(&max), sizeof(int));
	
	if (current_lenth == 0)
		return nullptr;

	char *message = new char[MESSAGE_SIZE];

	file.seekg(4 * sizeof(int) + (MESSAGE_SIZE) * first_element, std::ios::beg);
	file.read(message, MESSAGE_SIZE);

	first_element++;
	if (first_element > max - 1)
		first_element = 0;

	current_lenth--;
	
	file.seekp(0, std::ios::beg);
	file.write(reinterpret_cast<char *>(&first_element), sizeof(int));

	file.seekp(2 * sizeof(int), std::ios::beg);
	file.write(reinterpret_cast<char *>(&current_lenth), sizeof(int));

	return message;
}
void print(std::ostream& out)
{
	int first_element, current_lenth;
	
	file.seekg(0, std::ios::beg);
	file.read(reinterpret_cast<char *>(&first_element), sizeof(int));
	
	file.seekg(2 * sizeof(int), std::ios::beg);
	file.read(reinterpret_cast<char *>(&current_lenth), sizeof(int));
	
	file.seekg(4 * sizeof(int) + first_element * (MESSAGE_SIZE));

	char *temp_string = new char[MESSAGE_SIZE];


	for (int i = 0; i < current_lenth; i++) 
	{
		file.read(temp_string, MESSAGE_SIZE);
		out << temp_string << std::endl;
	}
}