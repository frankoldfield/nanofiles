package es.um.redes.nanoFiles.directory.message;

import java.nio.ByteBuffer;


public class DirMessage {

	public static final int PACKET_MAX_SIZE = 65536;

	public static final byte OPCODE_SIZE_BYTES = 1;

	private byte opcode;

	private String userName;

	public DirMessage(byte operation) {
		assert (operation == DirMessageOps.OPCODE_LOGIN);
		opcode = operation;
	}


	/*
	 * TODO: Añadir atributos y crear otros constructores específicos para crear
	 * mensajes con otros tipos de datos
	 * 
	 */
	public DirMessage(byte operation, String nick) {
		assert (opcode == DirMessageOps.OPCODE_REGISTER_USERNAME);
		/*
		 * TODO: Añadir al aserto el resto de opcodes de mensajes con los mismos campos
		 * (utilizan el mismo constructor)
		 */
		opcode = operation;
		userName = nick;
	}
	/**
	 * Método para obtener el tipo de mensaje (opcode)
	 * @return
	 */
	public byte getOpcode() {
		return opcode;
	}

	public String getUserName() {
		if (userName == null) {
			System.err.println(
					"PANIC: DirMessage.getUserName called but 'userName' field is not defined for messages of type "
							+ DirMessageOps.opcodeToOperation(opcode));
			System.exit(-1);
		}
		return userName;
	}


	/**
	 * Método de clase para parsear los campos de un mensaje y construir el objeto
	 * DirMessage que contiene los datos del mensaje recibido
	 * 
	 * @param data El
	 * @return
	 */
	public static DirMessage buildMessageFromReceivedData(byte[] data) {
		/*
		 * TODO: En función del tipo de mensaje, parsear el resto de campos para extraer
		 * los valores y llamar al constructor para crear un objeto DirMessage que
		 * contenga en sus atributos toda la información del mensaje
		 */
		return null;
	}

	/**
	 * Método para construir una solicitud de ingreso en el directorio
	 * 
	 * @return El array de bytes con el mensaje de solicitud de login
	 */
	public static byte[] buildLoginRequestMessage() {
		ByteBuffer bb = ByteBuffer.allocate(DirMessage.OPCODE_SIZE_BYTES);
		bb.put(DirMessageOps.OPCODE_LOGIN);
		return bb.array();
	}

	/**
	 * Método para construir una respuesta al ingreso del peer en el directorio
	 * 
	 * @param numServers El número de peer registrados como servidor en el
	 *                   directorio
	 * @return El array de bytes con el mensaje de solicitud de login
	 */
	public static byte[] buildLoginOKResponseMessage(int numServers) {
		ByteBuffer bb = ByteBuffer.allocate(DirMessage.OPCODE_SIZE_BYTES + Integer.BYTES);
		bb.put(DirMessageOps.OPCODE_LOGIN_OK);
		bb.putInt(numServers);
		return bb.array();
	}

	/**
	 * Método que procesa la respuesta a una solicitud de login
	 * 
	 * @param data El mensaje de respuesta recibido del directorio
	 * @return El número de peer servidores registrados en el directorio en el
	 *         momento del login, o -1 si el login en el servidor ha fallado
	 */
	public static int processLoginResponse(byte[] data) {
		ByteBuffer buf = ByteBuffer.wrap(data);
		byte opcode = buf.get();
		if (opcode == DirMessageOps.OPCODE_LOGIN_OK) {
			return buf.getInt(); // Return number of available file servers
		} else {
			return -1;
		}
	}

	/*
	 * TODO: Crear métodos buildXXXXRequestMessage/buildXXXXResponseMessage para
	 * construir mensajes de petición/respuesta
	 */
	// public static byte[] buildXXXXXXXResponseMessage(byte[] responseData)
	/*
	 * TODO: Crear métodos processXXXXRequestMessage/processXXXXResponseMessage para
	 * parsear el mensaje recibido y devolver un objeto según el tipo de dato que
	 * contiene, o boolean si es únicamente éxito fracaso.
	 */
	// public static boolean processXXXXXXXResponseMessage(byte[] responseData)

}
