from socket import*
msg="\r\n I love computer networks!"
endmsg="\r\n.\r\n"
 
# choose a mail server (e.g. a Google server) and call it mailserver
mailserver = 'gmail-smtp-in.l.google.com'
 
# create socket called clientSocket and establish a TCP connection with mailserver
 
#Fill in start  
clientSocket = socket(AF_INET, SOCK_STREAM)
clientSocket.connect((mailserver, 25))
 
#Fill in end
 
recv=clientSocket.recv(1024)
 
print recv
 
if recv[:3]!='220':
    print '220 reply not received from server.'
 
 
#Send HELO command and print server response.
 
heloCommand='HELO Alice\r\n'
 
clientSocket.send(heloCommand)
 
recv1=clientSocket.recv(1024)
 
print recv1
 
if recv1[:3]!='250':
    print '250 reply not received from server.'
 
 
 
#Send MAIL FROM command and print server response.
 
#Fill in start

clientSocket.send('MAIL FROM: <abhijit141088@gmail.com>\r\n')
recv2 = clientSocket.recv(1024)
 
print recv2
 
#Fill in end
 
 
 
#Send RCPT TO command and print server response.
 
#Fill in start

clientSocket.send('RCPT TO: <abhijit141088@gmail.com>\r\n')
recv2 = clientSocket.recv(1024)
print recv2
 
#Fill in end
 
 
 
#Send DATA command and print server response.
 
#Fill in start
clientSocket.send('DATA\r\n')
recv2 = clientSocket.recv(1024)
print recv2
 
#Fill in end
 
 
 
#send message data.
 
#Fill in start
 
clientSocket.send(msg)
 
#Fill in end
 
 
 
#message ends with a single period.
 
#Fill in start
 
clientSocket.send(endmsg)
 
#Fill in end
 
 
 
#send QUIT command and get server response.
 
#Fill in start
 
clientSocket.send('QUIT\r\n')
recv2 = clientSocket.recv(1024)
print recv2
 
#Fill in end

