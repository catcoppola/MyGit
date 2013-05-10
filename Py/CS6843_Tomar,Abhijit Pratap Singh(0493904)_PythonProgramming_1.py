from socket import *                                  

serverSocket = socket(AF_INET,SOCK_STREAM) 

serverSocket.bind(('192.168.1.103',8910))#I have used the IP instead of 'localhost'.That's how it runs on my sys	  

serverSocket.listen(1) 


while True: 
    print '\nReady to serve...\n' 
    connectionSocket, addr =  serverSocket.accept() 
    print '\nNow we are connected to the client',addr # Displays the name of the client
    try: 
        message = connectionSocket.recv(1024)                 
        filename = message.split(" ")[1]                  
        print '\nThe file requested by the client is\n',filename 
        f = open(filename[1:],'r')                         
        outputdata = f.read() 

        connectionSocket.send('HTTP/1.1 200 OK') 	#This is the HTTP OK response header 

        for i in range(0, len(outputdata)):            
            connectionSocket.send(outputdata[i])          

        connectionSocket.close() 

    except IOError: 
        connectionSocket.send('HTTP/1.1 404 Not Found') 	#This is the HTTP File not found response header 												                                                     
        print 'Error 404: Requested file not found on this server'  
        connectionSocket.close() 

serverSocket.close()

