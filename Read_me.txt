Some details regarding the implementation

myNode: It is the class that contains the main class. It separately spawns the server and keeps it running continuously to listen to any connections being made from the clients
Reads from the testing.txt file for input, so the destination processes and messages are configurable

myServer: This class contains the listener and also contains the m_receive module. Only when it receives the final timestamp, the message is processed.

testing.txt: this file contain the details of the destination processes and the messaage to be sent

order of the testing.txt:

All values are separated by ':'

The first value denotes: the source process
The second value denotes: the message to be sent
The remaining values denotes: the destination processes