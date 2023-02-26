package es.um.redes.nanoFiles.directory.connector;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import es.um.redes.nanoFiles.directory.message.DirMessage;

/**
 * Cliente con métodos de consulta y actualización específicos del directorio
 */
public class DirectoryConnector {
	/**
	 * Puerto en el que atienden los servidores de directorio
	 */
	private static final int DEFAULT_PORT = 6868;
	/**
	 * Tiempo máximo en milisegundos que se esperará a recibir una respuesta por el
	 * socket antes de que se deba lanzar una excepción SocketTimeoutException para
	 * recuperar el control
	 */
	private static final int TIMEOUT = 1000;
	/**
	 * Número de intentos máximos para obtener del directorio una respuesta a una
	 * solicitud enviada. Cada vez que expira el timeout sin recibir respuesta se
	 * cuenta como un intento.
	 */
	private static final int MAX_NUMBER_OF_ATTEMPTS = 5;

	/**
	 * Socket UDP usado para la comunicación con el directorio
	 */
	private DatagramSocket socket;
	/**
	 * Dirección de socket del directorio (IP:puertoUDP)
	 */
	private InetSocketAddress directoryAddress;

	public DirectoryConnector(String address) throws IOException {
		/*
		 * TODO: Crear el socket UDP para comunicación con el directorio durante el
		 * resto de la ejecución del programa, y guardar su dirección (IP:puerto) en
		 * atributos
		 */
		socket = new DatagramSocket();
		directoryAddress = new InetSocketAddress(InetAddress.getByName(address), DEFAULT_PORT);
	}

	/**
	 * Método para enviar y recibir datagramas al/del directorio
	 * 
	 * @param requestData los datos a enviar al directorio (mensaje de solicitud)
	 * @return los datos recibidos del directorio (mensaje de respuesta)
	 */
	public byte[] sendAndReceiveDatagrams(byte[] requestData) throws IOException {
		byte responseData[] = new byte[DirMessage.PACKET_MAX_SIZE];
		/*
		 * TODO: Enviar datos en un datagrama al directorio y recibir una respuesta.
		 * Debe implementarse un mecanismo de reintento usando temporizador, en caso de
		 * que no se reciba respuesta en el plazo de TIMEOUT. En caso de salte el
		 * timeout, se debe reintentar como máximo en MAX_NUMBER_OF_ATTEMPTS ocasiones
		 */
		/*
		 *DatagramPacket packetToServer = new DatagramPacket(requestData, requestData.length, directoryAddress);
		 *socket.send(packetToServer);
		 *DatagramPacket packetFromServer = new DatagramPacket(responseData, responseData.length);
		 *socket.receive(packetFromServer);
		 */
		
		DatagramPacket packetToServer = new DatagramPacket(requestData, requestData.length, directoryAddress);
		DatagramPacket packetFromServer = new DatagramPacket(responseData, responseData.length);
		int i = 0;
		while (i < MAX_NUMBER_OF_ATTEMPTS) { //Bucle para reintentos
			socket.send(packetToServer); //Enviamos el paquete al servidor

			socket.setSoTimeout(TIMEOUT);//Establecemos el timeout al valor que hemos declarado mas arriba

			try {
				socket.receive(packetFromServer); //Intentamos recibir el paquete
			} catch (SocketTimeoutException e) { //Si hay un timeout
				i++;//Aumentamos i para indicar que hemos hecho un intento
				System.err.println("Trying again...");//Salida de error
				continue;
			}
			break;
		}
		if (i == MAX_NUMBER_OF_ATTEMPTS) { //Si hemos sobrepasado el número máximo deintentos y hemos salido del bucle es que no se ha podido enviar
			responseData = null;
		}
		return responseData;
	}

	public int logIntoDirectory() throws IOException{ // Returns number of file servers
		byte[] requestData = DirMessage.buildLoginRequestMessage();
		byte[] responseData = this.sendAndReceiveDatagrams(requestData);
		return DirMessage.processLoginResponse(responseData);
	}
	/*
	 * TODO: Crear un método distinto para cada intercambio posible de mensajes con
	 * el directorio, basándose en logIntoDirectory o registerNickname, haciendo uso
	 * de los métodos adecuados de DirMessage para construir mensajes de petición y
	 * procesar mensajes de respuesta
	 */
	public boolean registerNickname(String nick) {
		// byte[] requestData = DirMessage.buildXXXXXXMessage(nick);
		// byte[] responseData = this.sendAndReceiveDatagrams(requestData);
		// return DirMessage.processXXXXXXResponseMessage(responseData);
		return false;
	}

}
