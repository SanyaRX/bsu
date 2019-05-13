#include <iostream>
#include <fstream>

struct tax_payment
{
	int num;
	char name[10];
	double sum;
};

bool checkPayment(char symbol, double file_sum, double payment) 
{
	if (symbol == '<' && payment < file_sum)
	{
		return true;
	} 
	else if(symbol == '>' && payment > file_sum)
	{
		return true;
	}
	return false;
}

int main(int argc, char *argv[])
{
	if (argc != 5) {
		std::cout << "Invalid number of parameters\n";
		system("pause");
		return 0;
	}

	std::ifstream fin(argv[1], std::ios::binary | std::ios::in);
	std::ofstream fout(argv[2], std::ios::out);
	
	double payment_sum = std::atof(argv[3]);
	char symbol = argv[4][0];

	

	fout << "\tReport file: " << argv[1] << std::endl;
	fout << "List of companies, with tax payments " << symbol << " " << payment_sum << std::endl;
	int i = 0;
	while (fin.peek() != EOF)
	{
		tax_payment temp;
		fin.read(reinterpret_cast<char*>(&temp), sizeof(tax_payment));
		if (checkPayment(symbol, payment_sum, temp.sum))
		{
			fout << ++i << ") Identify number: " << temp.num << "; Company name: " << temp.name
				<< "; Sum of tax payments: " << temp.sum << std::endl;
		}
	}
	
	fin.close();
	fout.close();
	return 0;
}