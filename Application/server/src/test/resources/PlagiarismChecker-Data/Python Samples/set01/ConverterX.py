import NumberStringConstants

class ConverterX(object):

    numberString=""
    number=""
    numNames = NumberStringConstants.digits
    tensNames = NumberStringConstants.tens

    def convertToWords(self, number) :

        ste=""

        if number % 100 < 20:
            ste = self.numNames[int(number % 100)]
            number /= 100
        else:
            ste = self.numNames[int(number % 10)]
            number /= 10
            ste = self.tensNames[int(number % 10)] + ste
            number /= 10

        if number == 0:

            return ste

        else:

           return self.numNames[int(number)] + " hundred" + ste
