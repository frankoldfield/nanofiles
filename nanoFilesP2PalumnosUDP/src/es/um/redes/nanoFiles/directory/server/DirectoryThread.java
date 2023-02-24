package es.um.redes.nanoFiles.directory.server;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.HashMap;

import es.um.redes.nanoFiles.directory.message.DirMessage;
import es.um.redes.nanoFiles.directory.message.DirMessageOps;
import es.um.redes.nanoFiles.util.FileInfo;

public class DirectoryThread extends Thread {

	/**
	 * Socket de comunicación UDP con el cliente UDP (DirectoryConnector)
	 */
	protected DatagramSocket socket = null;

	/**
	 * Probabilidad de descartar un mensaje recibido en el directorio (para simular
	 * enlace no confiable y testear el código de retransmisión)
	 */
	protected double messageDiscardProbability;

	/**
	 * Estructura para guardar los nicks de usuarios registrados, y la fecha/hora de
	 * registro
	 * 
	 */
	private HashMap<String, LocalDateTime> nicks;
	/**
	 * Estructura para guardar los usuarios servidores (nick, direcciones de socket
	 * TCP)
	 */
	// TCP)
	private HashMap<String, InetSocketAddress> servers;
	/**
	 * Estructura para guardar la lista de ficheros publicados por todos los peers
	 * servidores, cada fichero identificado por su hash
	 */
	private HashMap<String, FileInfo> files;

	public DirectoryThread(int directoryPort, double corruptionProbability) throws SocketException {
		/*
		 * TODO: Crear dirección de socket con el puerto en el que escucha el directorio
		 */
		// TODO: Crear el socket UDP asociado a la dirección de socket anterior
		messageDiscardProbability = corruptionProbability;

	}

	public void run() {
		byte[] receptionBuffer = new byte[DirMessage.PACKET_MAX_SIZE];
		DatagramPacket requestPacket = new DatagramPacket(receptionBuffer, receptionBuffer.length);
		InetSocketAddress clientId = null;

		System.out.println("Directory starting...");

		while (true) {
			try {

				// TODO: Recibimos a través del socket el datagrama con mensaje de solicitud

				// TODO: Averiguamos quién es el cliente

				// Vemos si el mensaje debe ser descartado por la probabilidad de descarte

				double rand = Math.random();
				if (rand < messageDiscardProbability) {
					System.err.println("Directory DISCARDED datagram from " + clientId);
					continue;
				}

				// Analizamos la solicitud y la procesamos

				if (requestPacket.getData().length > 0) {
					processRequestFromClient(requestPacket.getData(), clientId);
				} else {
					System.err.println("Directory received EMPTY datagram from " + clientId);
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Directory received EMPTY datagram from " + clientId);				
				break;
			}
		}
		// Cerrar el socket
		socket.close();
	}

	// Método para procesar la solicitud enviada por clientAddr
	public void processRequestFromClient(byte[] data, InetSocketAddress clientAddr) throws IOException {
		// TODO: Construir un objeto mensaje (DirMessage) a partir de los datos recibidos
		// PeerMessage.buildMessageFromReceivedData(data)
		
		// TODO: Actualizar estado del directorio y enviar una respuesta en función del
		// tipo de mensaje recibido
	}

	// Método para enviar la confirmación del registro
	private void sendLoginOK(InetSocketAddress clientAddr) throws IOException {
		// TODO: Construir el datagrama con la respuesta y enviarlo por el socket al cliente
		// byte[] responseData = DirMessage.buildXXXXXXXResponseMessage();
	}


}
