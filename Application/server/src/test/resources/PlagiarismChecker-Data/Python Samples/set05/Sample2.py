class Node:
    def __init__(self, data):
        self.next = None
        self.prev = None
        self.data = data


class LinkedList:

    def remove(self, p):
        tmp = p.prev
        p.prev.next = p.next
        p.prev = tmp

    def search(self, k):
        p = self.head
        if p is None:
            return None
        else:
            while p.next is not None:
                if p.data == k:
                    return p
                p = p.next
            if p.data == k:
                return p
        return None

    def add(self, data):
        node = Node(data)
        if self.head is not None:
            node.next = self.head
            node.next.prev = node
            self.head = node
        else:
            self.head = node

    def __init__(self):
        self.head = None

    def __str__(self):
        s = ""
        p = self.head
        if p is None:
            return s
        else:
            while p.next is not None:
                s += p.data
                p = p.next
            s += p.data
        return s