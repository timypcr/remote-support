#!/usr/bin/python3

# To test: run server on one computer, and virtualbox with nat on two others
# Currently linux-only. When run as root, with server.py at public address,
# traffic sent to 10.8.0.[username] will arrive at the other client
# TODO Authenticate with auth server and destination, encryption using DTLS library, prompt user to accept connection
# TODO MTU discovery
# TODO ask dest for tun ip address, give error if they are both the same
# TODO Make simple gui to allow the connection details to be entered
# TODO Forward ssh and vnc from gui
# TODO Fall back on upnp, or ask the user to forward ports if udp hole punching doesn't work

from twisted.internet import reactor
from peer import PeerConnection
from tunnel import Tunnel

conn = PeerConnection(input("What is your username?"),
                      input("Who would you like to connect to?"),
                      ('172.16.1.216', 40000),  # dev: ('172.16.1.216', 40000), public ('63.135.27.26', 40000)
                      lambda data: tunnel_thread.receive_peer_data(data))
tunnel_thread = Tunnel(conn)
tunnel_thread.start()

reactor.listenUDP(40000, conn)
reactor.run()
