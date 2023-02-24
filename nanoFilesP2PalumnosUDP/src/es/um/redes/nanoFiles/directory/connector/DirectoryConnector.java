package es.um.redes.nanoFiles.directory.connector;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
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
	}

	/**
	 * Método para enviar y recibir datagramas al/del directorio
	 * 
	 * @param requestData los datos a enviar al directorio (mensaje de solicitud)
	 * @return los datos recibidos del directorio (mensaje de respuesta)
	 */
	public byte[] sendAndReceiveDatagrams(byte[] requestData) {
		byte responseData[] = new byte[DirMessage.PACKET_MAX_SIZE];
		/*
		 * TODO: Enviar datos en un datagrama al directorio y recibir una respuesta.
		 * Debe implementarse un mecanismo de reintento usando temporizador, en caso de
		 * que no se reciba respuesta en el plazo de TIMEOUT. En caso de salte el
		 * timeout, se debe reintentar como máximo en MAX_NUMBER_OF_ATTEMPTS ocasiones
		 */
		return responseData;
	}

	public int logIntoDirectory() { // Returns number of file servers
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
