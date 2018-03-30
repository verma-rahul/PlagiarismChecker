class Tokenize(object):
    def __init__(self, string):
        """Return a Customer object whose name is *name* and starting
        balance is *balance*."""
        self.s = string
        self.SIZE = len(string)
    
    def tokenize(self):
        array = self.s.split(" ");
        return array

sample = "This is a sentence"
tokenize_obj = Tokenize(sample)
tokenize_obj.tokenize()