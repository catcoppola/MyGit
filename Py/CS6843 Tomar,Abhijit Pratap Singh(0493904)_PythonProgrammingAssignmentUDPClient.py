# CS6843 Python Programming Assignment UDP Client 		 Tomar,Abhijit Pratap Singh 0493904
from socket import *
import time

seqNum = 0

clientSocket = socket(AF_INET, SOCK_DGRAM)
while seqNum < 10:
	start = time.time()
	clientmessage = 'ping'+' '+str(seqNum)+' '+str(start)
	print clientmessage
	clientSocket.sendto(clientmessage,('localhost', 12000))
	
	clientSocket.settimeout(1.0000000000000)
	try:
		newmessage, serverAddress = clientSocket.recvfrom(2048)
		rtt = (time.time()-start)
		print  str(seqNum)+' '+newmessage+' '+'Total Round Trip Time: '+str(rtt)+'\n\n'
		 
	except timeout:
		print str(seqNum)+' lost packet\n\n'
	
	start = 0;
	rtt = 0;
	seqNum = seqNum+1;

clientSocket.close()