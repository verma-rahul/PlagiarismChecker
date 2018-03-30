class DLLNode:
    def __init__(self, val):
        self.val = val
        self.n = None
        self.p = None


class DoubleLinkedList:
    def __init__(self):
        self.h = None

    def add_to_head(self, val):
        new_node = DLLNode(val)
        if self.h is None:
            self.h = new_node
        else:
            new_node.n = self.h
            new_node.n.p = new_node
            self.h = new_node

    def find(self, k):
        curr_node = self.h
        if curr_node is not None:
            while curr_node.n is not None:
                if curr_node.val == k:
                    return curr_node
                curr_node = curr_node.n
            if curr_node.val == k:
                return curr_node
        return None

    def delete(self, node):
        temp = node.p
        node.p.n = node.n
        node.p = temp

    def __str__(self):
        op = ""
        curr_node = self.h
        if curr_node is not None:
            while curr_node.n is not None:
                op += curr_node.val
                op = op.n
            op += curr_node.val
        return op