tens = ["",
			    " ten",
			    " twenty",
			    " thirty",
			    " forty",
			    " fifty",
			    " sixty",
			    " seventy",
			    " eighty",
			    " ninety"]

digits = [
			    "",
			    " one",
			    " two",
			    " three",
			    " four",
			    " five",
			    " six",
			    " seven",
			    " eight",
			    " nine",
			    " ten",
			    " eleven",
			    " twelve",
			    " thirteen",
			    " fourteen",
			    " fifteen",
			    " sixteen",
			    " seventeen",
			    " eighteen",
			    " nineteen"
    ]

number = 234
ste=""
numberString=""

if number % 100 < 20:
    ste = digits[int(number % 100)]
    number /= 100;
else:
    ste = digits[int(number % 10)]
    number /= 10
    ste = tens[int(number % 10)] + ste
    number /= 10


if (number == 0):
    numberString = ste

numberString = digits[int(number)] + " hundred" + ste
print(numberString)