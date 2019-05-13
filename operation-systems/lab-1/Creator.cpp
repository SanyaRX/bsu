#include <iostream>
#include <fstream>

struct tax_payment
{
	int num;
	char name[10];
	double sum;
};

int main(int argc, char *argv[])
{
	if (argc != 3)
	{
		std::cout << "Invalid number of parameters\n";
		system("pause");
		return 0;
	}

	std::ofstream fout(argv[1], std::ios::out | std::ios::binary);

	int payment_number = std::atoi(argv[2]);

	for (int i = 0; i < payment_number; i++)
	{
		tax_payment temp;
		std::cout << "---Payment #" << i + 1 << "---\n";
		
		std::cout << "Enter company num" << ": ";
		std::cin >> temp.num;
		
		std::cout << "\nEnter company name: ";
		std::cin >> temp.name;

		std::cout << "\nEnter company sum: ";
		std::cin >> temp.sum;

		fout.write(reinterpret_cast<char*>(&temp), sizeof(tax_payment));

		std::cout << "\n";
	}

	fout.close();
	return 0;
}