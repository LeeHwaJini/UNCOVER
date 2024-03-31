package com.shsoftnet.shcommon.utils.bytes;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;

public class ByteOrderUtil {

	public final static int SHORT_OFFSET 	= 2;
	public final static int FLOAT_OFFSET 	= 4;
	public final static int INT_OFFSET 		= 4;
	public final static int DOUBLE_OFFSET 	= 8;
	public final static int LONG_OFFSET 	= 8;

	public static final int BYTE_ORDER_LITTLE_ENDIAN = 0;
	public static final int BYTE_ORDER_BIG_ENDIAN = 1;

//	public static int BYTE_ORDER = BYTE_ORDER_LITTLE_ENDIAN;//BYTE_ORDER_BIG_ENDIAN;
	public static int BYTE_ORDER = BYTE_ORDER_BIG_ENDIAN;//BYTE_ORDER_BIG_ENDIAN;


	public static byte[] ApplyByteOrder(byte[] rawdata, int order) {
		if(order == BYTE_ORDER_LITTLE_ENDIAN) {
			return ByteBuffer.wrap(rawdata).order(ByteOrder.BIG_ENDIAN).array();
		}else{
			return ByteBuffer.wrap(rawdata).order(ByteOrder.LITTLE_ENDIAN).array();
		}
	}

	static final int[] lookup = {0x0, 0x1, 0x3, 0x7, 0xF, 0x1F, 0x3F, 0x7F, 0xFF, 0x1FF, 0x3FF, 0x7FF, 0xFFF, 0x1FFF, 0x3FFF, 0x7FFF, 0xFFFF };
	public static int getBitSeqAsInt(byte[] bytes, int offset, int len){

		int byteIndex = offset / 8;
		int bitIndex = offset % 8;
		int val;

		if ((bitIndex + len) > 16) {
			val = ((bytes[byteIndex] << 16 | bytes[byteIndex + 1] << 8 | bytes[byteIndex + 2]) >> (24 - bitIndex - len)) & lookup[len];
		} else if ((offset + len) > 8) {
			val = ((bytes[byteIndex] << 8 | bytes[byteIndex + 1]) >> (16 - bitIndex - len)) & lookup[len];
		} else {
			val = (bytes[byteIndex] >> (8 - offset - len)) & lookup[len];
		}

		return val;
	}
	//  8bit
	// 3~6 =
	// start 3
	// offset 3
	// 0111 0110
	// 1. 8 - (start+offset) 만큼 right shift
	// 1101 1000
	// 2. 8-offset left shift
	// 0000 0110
//	public static byte BitFunc_Get(byte value, int start, int offset){
//		byte tmp = (byte) (value << ( 8 - (start + offset)));
//		 tmp = (byte) (tmp >> ( 8 - offset));
//		return tmp;
//	}

	/**
	 *
	 * @param value
	 * @param index start index 0
	 * @param range start range 1
	 * @return
	 */
	public static int BitFunc_Get(long value, int index, int range){
		if(range <= 1) {
			if( ((value >> (index)) & 1) != 0) {
				return 1<<index;
			}else{
				return 0;
			}
		}else{
			int tmp = 0;
			if( (1L<<(index+range) > value ) ){
				return 0;
			}
			for(int i = index ; i < (index + range); i++) {
				if( ((value >> (i)) & 1) != 0  ) {
					tmp += 1L<<i;
				}

			}
			return  tmp;
		}
	}


	/**
	 *
	 * @param value
	 * @param index start index 0
	 * @param range start range 1
	 * @return
	 */
	public static int BitFunc_Get_that_value(long value, int index, int range){
		if(range <= 1) {
			return (int)((value >> (index)) & 1);
		}else{
			int tmp = 0;
//			if( (1L<<(index+range)) > value  ){
//				return 0;
//			}
			for(int i = 0 ; i < range; i++) {
				if( ((value >> (index + i)) & 1) != 0  ) {
					tmp += 1L<<i;
				}
			}
			return  tmp;
		}
	}

	public static long extractSub(final long l, final int nrBits, final int offset)
	{
		final long rightShifted = l >>> offset;
		final long mask = (1L << nrBits) - 1L;
		return rightShifted & mask;
	}


//	public static byte BitFunc_Get(short value, int start, int offset){
//		short tmp = (short) (value << ( 16 - (start + offset)));
//		tmp = (short) (tmp >> ( 16 - offset));
//		return (byte) tmp;
//	}

	public static boolean BitFunc_isSet(byte value, int bit){
		return (value&(1<<bit))!=0;
	}

	public static boolean BitFunc_isSet(int value, int bit){
		return (value&(1<<bit))!=0;
	}

	public static boolean BitFunc_isSet(long value, int bit){
		return (value&(1L<<bit))!=0;
	}

	public static boolean BitFunc_isSet(short value, int bit){
		return (value&(1<<bit))!=0;
	}

	// returns a byte with the required bit set
	public static byte BitFunc_set(byte value, int bit){
		return (byte) (value|(1<<bit));
	}

	// returns a byte with the required bit set
	public static short BitFunc_set(short value, int bit){
		return (short) (value|(1<<bit));
	}

	public static int BitFunc_set(int value, int bit){
		return (int) (value|(1<<bit));
	}

	public static long BitFunc_set(long value, int bit){
		return (long) (value|(1L<<bit));
	}

	public static String ByteArrayToHexString(byte[] bytes){
		String hexText = new BigInteger(bytes).toString(16);
		return hexText;
	}

/*	public static byte[] HexStringToByteArray(String hexString){
		byte[] bytes = new java.math.BigInteger(hexString, 16).toByteArray();
		return bytes;
	}*/

	//Converting a string of hex character to bytes
	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2){
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
					+ Character.digit(s.charAt(i+1), 16));
		}
		return data;
	}

	//Converting a bytes array to string of hex character
	public static String byteArrayToHexString(byte[] b) {
		int len = b.length;
		String data = new String();

		System.out.println(String.format( "[BYTE_UTIL] BYTE ARRAY TO HEX CHAR START(SIZE=%d)",len  ));

		for (int i = 0; i < len; i++){
			data += Integer.toHexString((b[i] >> 4) & 0xf);
			data += Integer.toHexString(b[i] & 0xf);
		}
		System.out.println(String.format( "[BYTE_UTIL] BYTE ARRAY TO HEX CHAR END(SIZE=%d)",len  ));
		return data;
	}


	public static String byteArrayToHexStringEx(byte[] bytes) {
		System.out.println(String.format( "[BYTE_UTIL] BYTE ARRAY TO HEX CHAR START(SIZE=%d)",bytes.length  ));
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		for(byte b: bytes)
			sb.append(String.format("%02x", b));
		System.out.println(String.format( "[BYTE_UTIL] BYTE ARRAY TO HEX CHAR END(SIZE=%d)",bytes.length  ));
		return sb.toString();
	}

	public static String byteArrayToHexStringEx2(byte[] bytes) {
		System.out.println(String.format( "[BYTE_UTIL] BYTE ARRAY TO HEX CHAR START(SIZE=%d)",bytes.length  ));
		String result = new BigInteger(1, bytes).toString(16);
		System.out.println(String.format( "[BYTE_UTIL] BYTE ARRAY TO HEX CHAR END(SIZE=%d)",result.length()  ));
		return result;
	}


	private static HashMap<Byte,char[]> byteToHex=new HashMap<Byte, char[]>();
	static
	{   for(int i=0;i<16;++i)
		byteToHex.put((byte)i, new char[]{'0',Integer.toHexString(i).charAt(0)});
		for(int i=16;i<256;++i)
			byteToHex.put((byte)i, Integer.toHexString(i).toCharArray());
	}
	public static String byteArrayToHexStringEx3(byte[]bytes)
	{
		System.out.println(String.format( "[BYTE_UTIL] BYTE ARRAY TO HEX CHAR START(SIZE=%d)",bytes.length  ));
		StringBuilder stringBuilder = new StringBuilder(bytes.length*2);
		for(byte b:bytes)
			stringBuilder.append(byteToHex.get(b));
		System.out.println(String.format( "[BYTE_UTIL] BYTE ARRAY TO HEX CHAR END(SIZE=%d)",bytes.length  ));
		return stringBuilder.toString();
	}


	public static int getInt(byte[] buffer, int offset, ByteOrder byteOrder)
	{
		if(byteOrder == ByteOrder.BIG_ENDIAN)
			return (int)((buffer[offset]&0xFF)<<24)|(int)((buffer[offset+1]&0xFF)<<16)|
					(int)((buffer[offset+2]&0xFF)<<8)|(int)((buffer[offset+3]&0xFF));
		else
			return (int)((buffer[offset+3]&0xFF)<<24)|(int)((buffer[offset+2]&0xFF)<<16)|
					(int)((buffer[offset+1]&0xFF)<<8)|(int)((buffer[offset]&0xFF));
	}
	public static int getInt(byte[] buffer, int offset)
	{
		if(BYTE_ORDER == BYTE_ORDER_BIG_ENDIAN)
			return (int)((buffer[offset]&0xFF)<<24)|(int)((buffer[offset+1]&0xFF)<<16)|
					(int)((buffer[offset+2]&0xFF)<<8)|(int)((buffer[offset+3]&0xFF));
		else
			return (int)((buffer[offset+3]&0xFF)<<24)|(int)((buffer[offset+2]&0xFF)<<16)|
					(int)((buffer[offset+1]&0xFF)<<8)|(int)((buffer[offset]&0xFF));
	}

	public static short getShort(byte[] buffer, int offset, ByteOrder byteOrder)
	{
		if(byteOrder == ByteOrder.BIG_ENDIAN)
			return (short)(((0xFF & buffer[offset]) << 8) | (0xFF & buffer[offset+1]));
		else
			return (short)(((0xFF & buffer[offset+1]) << 8) | (0xFF & buffer[offset]));
	}
	public static short getShort(byte[] buffer, int offset)
	{
		if(BYTE_ORDER == BYTE_ORDER_BIG_ENDIAN)
			return (short)(((0xFF & buffer[offset]) << 8) | (0xFF & buffer[offset+1]));
		else
			return (short)(((0xFF & buffer[offset+1]) << 8) | (0xFF & buffer[offset]));
	}


	public static float getFloat(ByteBuffer floatByteBuffer, ByteOrder byteOrder)
	{
		if(byteOrder == ByteOrder.BIG_ENDIAN)
			return floatByteBuffer.order(ByteOrder.BIG_ENDIAN).getFloat();
		else
			return floatByteBuffer.order(ByteOrder.LITTLE_ENDIAN).getFloat();
	}
	public static float getFloat(ByteBuffer floatByteBuffer)
	{
		if(BYTE_ORDER == BYTE_ORDER_BIG_ENDIAN)
			return floatByteBuffer.order(ByteOrder.BIG_ENDIAN).getFloat();
		else
			return floatByteBuffer.order(ByteOrder.LITTLE_ENDIAN).getFloat();
	}

	public static float fromByteArray(byte[] bytes) {
		return bytes[0] << 24 | (bytes[1] & 0xFF) << 16 | (bytes[2] & 0xFF) << 8 | (bytes[3] & 0xFF);
	}

	public static float getFloat(byte[] dataBuffer, int dataOffset, ByteOrder byteOrder)
	{
		if(byteOrder == ByteOrder.BIG_ENDIAN)
			return ByteBuffer.wrap(dataBuffer, dataOffset, 4).order(ByteOrder.BIG_ENDIAN).getFloat();
		else
			//	return floatByteBuffer.order(ByteOrder.LITTLE_ENDIAN).getFloat();
			return bytes2float(dataBuffer, dataOffset);
	}
	public static float getFloat(byte[] dataBuffer, int dataOffset)
	{
		if(BYTE_ORDER == BYTE_ORDER_BIG_ENDIAN)
			return ByteBuffer.wrap(dataBuffer, dataOffset, 4).order(ByteOrder.BIG_ENDIAN).getFloat();
		else
			//	return floatByteBuffer.order(ByteOrder.LITTLE_ENDIAN).getFloat();
			return bytes2float(dataBuffer, dataOffset);
	}

	public static final long getLong(byte[] dataBuffer, int dataOffset) {
		return getLong(dataBuffer, dataOffset, BYTE_ORDER);
	}

	public static final long getLong(byte[] dataBuffer, int dataOffset, int byteOrder) {
		long value = 0;

		if (byteOrder == BYTE_ORDER_BIG_ENDIAN) {
			value = ((long) (dataBuffer[dataOffset + 4] & 0xff) << 56) |
					((long) (dataBuffer[dataOffset + 5] & 0xff) << 48) |
					((long) (dataBuffer[dataOffset + 6] & 0xff) << 40) |
					((long) (dataBuffer[dataOffset + 7] & 0xff) << 32) |
					((long) (dataBuffer[dataOffset] & 0xff) << 24) |
					((long) (dataBuffer[dataOffset + 1] & 0xff) << 16) |
					((long) (dataBuffer[dataOffset + 2] & 0xff) << 8) |
					((long) (dataBuffer[dataOffset + 3] & 0xff));

			if ((value & 0xffffffffL) == 0) {
				value = ByteBuffer.wrap(dataBuffer, dataOffset, 8).order(ByteOrder.BIG_ENDIAN).getLong();
				String str2 = Long.toHexString(value);
			}

			return value;
		} else {
			return ByteBuffer.wrap(dataBuffer, dataOffset, 8).order(ByteOrder.LITTLE_ENDIAN).getLong();
		}
	}

	public static final long getLong(byte[] dataBuffer, int dataOffset, ByteOrder byteOrder) {
		if (byteOrder == ByteOrder.BIG_ENDIAN) {
			return getLong(dataBuffer, dataOffset, BYTE_ORDER_BIG_ENDIAN);
		} else {
			return ByteBuffer.wrap(dataBuffer, dataOffset, 8).order(ByteOrder.LITTLE_ENDIAN).getLong();
		}
	}

	//check the this url : http://bbaeggar.tistory.com/57
	public static float bytes2float (byte[] bytes, int start) {
		int i = 0;
		int len = 4;
		int cnt = 0;
		byte[] tmp = new byte[len];

		for (i = start; i < (start + len); i++) {
			tmp[cnt] = bytes[i];
			cnt++;
		}

		int accum = 0;
		i = 0;
		for ( int shiftBy = 0; shiftBy < 32; shiftBy += 8 ) {
			accum |= ( (long)( tmp[i] & 0xff ) ) << shiftBy;
			i++;
		}

		float f_value = Float.intBitsToFloat(accum);


		tmp = null;
		return f_value;
	}

	public static double getDouble(byte[] dataBuffer, int dataOffset, ByteOrder byteOrder)
	{
		if(byteOrder == ByteOrder.BIG_ENDIAN)
		{
			return ByteBuffer.wrap(dataBuffer, dataOffset, 8).order(ByteOrder.BIG_ENDIAN).getDouble();
		}
		else
		{
			long temp = ((long)getInt(dataBuffer, dataOffset+4, ByteOrder.LITTLE_ENDIAN) <<32) | ((long) getInt(dataBuffer,dataOffset, ByteOrder.LITTLE_ENDIAN) & 0xffffffffL );
			return Double.longBitsToDouble(temp);
		}
	}
	public static double getDouble(byte[] dataBuffer, int dataOffset)
	{
		if(BYTE_ORDER == BYTE_ORDER_BIG_ENDIAN)
		{
			return ByteBuffer.wrap(dataBuffer, dataOffset, 8).order(ByteOrder.BIG_ENDIAN).getDouble();
		}
		else
		{
			long temp = ((long)getInt(dataBuffer, dataOffset+4) <<32) | ((long) getInt(dataBuffer,dataOffset) & 0xffffffffL );
			return Double.longBitsToDouble(temp);
		}
	}

	public static byte[] int2byte(int value, ByteOrder byteOrder) {
		byte[] bytes=new byte[4];
		if(byteOrder == ByteOrder.BIG_ENDIAN)
		{
			bytes[0]=(byte)((value&0xFF000000)>>24);
			bytes[1]=(byte)((value&0x00FF0000)>>16);
			bytes[2]=(byte)((value&0x0000FF00)>>8);
			bytes[3]=(byte) (value&0x000000FF);
		}
		else
		{
			bytes[3]=(byte)((value&0xFF000000)>>24);
			bytes[2]=(byte)((value&0x00FF0000)>>16);
			bytes[1]=(byte)((value&0x0000FF00)>>8);
			bytes[0]=(byte) (value&0x000000FF);
		}
		byte[] tmp = bytes.clone();
		bytes = null;
		return tmp;//bytes.clone();
	}
	public static byte[] int2byte(int value) {
		byte[] bytes=new byte[4];
		if(BYTE_ORDER == BYTE_ORDER_BIG_ENDIAN)
		{
			bytes[0]=(byte)((value&0xFF000000)>>24);
			bytes[1]=(byte)((value&0x00FF0000)>>16);
			bytes[2]=(byte)((value&0x0000FF00)>>8);
			bytes[3]=(byte) (value&0x000000FF);
		}
		else
		{
			bytes[3]=(byte)((value&0xFF000000)>>24);
			bytes[2]=(byte)((value&0x00FF0000)>>16);
			bytes[1]=(byte)((value&0x0000FF00)>>8);
			bytes[0]=(byte) (value&0x000000FF);
		}
		byte[] tmp = bytes.clone();
		bytes = null;
		return tmp;//bytes.clone();
	}


	public static byte[] int2byte(byte[] bytes, int value, ByteOrder byteOrder) {
		if(bytes.length == 4) {
			if (byteOrder == ByteOrder.BIG_ENDIAN) {
				bytes[0] = (byte) ((value & 0xFF000000) >> 24);
				bytes[1] = (byte) ((value & 0x00FF0000) >> 16);
				bytes[2] = (byte) ((value & 0x0000FF00) >> 8);
				bytes[3] = (byte) (value & 0x000000FF);
			} else {
				bytes[3] = (byte) ((value & 0xFF000000) >> 24);
				bytes[2] = (byte) ((value & 0x00FF0000) >> 16);
				bytes[1] = (byte) ((value & 0x0000FF00) >> 8);
				bytes[0] = (byte) (value & 0x000000FF);
			}
		}
		return bytes;
	}
	public static byte[] int2byte(byte[] bytes, int value) {
		if(bytes.length == 4) {
			if (BYTE_ORDER == BYTE_ORDER_BIG_ENDIAN) {
				bytes[0] = (byte) ((value & 0xFF000000) >> 24);
				bytes[1] = (byte) ((value & 0x00FF0000) >> 16);
				bytes[2] = (byte) ((value & 0x0000FF00) >> 8);
				bytes[3] = (byte) (value & 0x000000FF);
			} else {
				bytes[3] = (byte) ((value & 0xFF000000) >> 24);
				bytes[2] = (byte) ((value & 0x00FF0000) >> 16);
				bytes[1] = (byte) ((value & 0x0000FF00) >> 8);
				bytes[0] = (byte) (value & 0x000000FF);
			}
		}
		return bytes;
	}

	public static byte[] float2bytes(float value, ByteOrder byteOrder) {

		byte[] array = new byte[4];

		int intBits=Float.floatToIntBits(value);

		if(byteOrder == ByteOrder.BIG_ENDIAN)
		{
			array[3]=(byte)((intBits&0x000000FF)>>0);
			array[2]=(byte)((intBits&0x0000FF00)>>8);
			array[1]=(byte)((intBits&0x00FF0000)>>16);
			array[0]=(byte)((intBits&0xFF000000)>>24);
		}
		else
		{
			array[0]=(byte)((intBits&0x000000FF)>>0);
			array[1]=(byte)((intBits&0x0000FF00)>>8);
			array[2]=(byte)((intBits&0x00FF0000)>>16);
			array[3]=(byte)((intBits&0xFF000000)>>24);
		}
		byte[] tmp = array.clone();
		array = null;
		return tmp;
	}
	public static byte[] float2bytes(float value) {

		byte[] array = new byte[4];

		int intBits=Float.floatToIntBits(value);

		if(BYTE_ORDER == BYTE_ORDER_BIG_ENDIAN)
		{
			array[3]=(byte)((intBits&0x000000FF)>>0);
			array[2]=(byte)((intBits&0x0000FF00)>>8);
			array[1]=(byte)((intBits&0x00FF0000)>>16);
			array[0]=(byte)((intBits&0xFF000000)>>24);
		}
		else
		{
			array[0]=(byte)((intBits&0x000000FF)>>0);
			array[1]=(byte)((intBits&0x0000FF00)>>8);
			array[2]=(byte)((intBits&0x00FF0000)>>16);
			array[3]=(byte)((intBits&0xFF000000)>>24);
		}
		byte[] tmp = array.clone();
		array = null;
		return tmp;
	}

	public static byte[] double2bytes(double value, ByteOrder byteOrder) {
		return longToByte(Double.doubleToRawLongBits(value), byteOrder);
	}
	public static byte[] double2bytes(double value) {
		return longToByte(Double.doubleToRawLongBits(value));
	}

	public static byte[] short2bytes(short value, ByteOrder byteOrder) {

		byte[] bytes=new byte[2];

		if(byteOrder == ByteOrder.BIG_ENDIAN)
		{
			bytes[0]=(byte)((value&0xFF00)>>8);
			bytes[1]=(byte)((value&0x00FF)>>0);
		}
		else
		{
			bytes[1]=(byte)((value&0xFF00)>>8);
			bytes[0]=(byte)((value&0x00FF)>>0);
		}

		return bytes;
	}
	public static byte[] short2bytes(short value) {

		byte[] bytes=new byte[2];

		if(BYTE_ORDER == BYTE_ORDER_BIG_ENDIAN)
		{
			bytes[0]=(byte)((value&0xFF00)>>8);
			bytes[1]=(byte)((value&0x00FF)>>0);
		}
		else
		{
			bytes[1]=(byte)((value&0xFF00)>>8);
			bytes[0]=(byte)((value&0x00FF)>>0);
		}

		return bytes;
	}

	public static final byte[] longToByte( long l, ByteOrder byteOrder) {
		byte[] dest = new byte[8];

		if(byteOrder == ByteOrder.BIG_ENDIAN)
		{
			for(int i = 0; i < dest.length; i++)
			{
				dest[i] = (byte)(l >> ((dest.length - 1 - i) *8) & 0xff);
			}
		}
		else
		{
			for(int i = 0; i < dest.length; i++)
			{
				dest[i] = (byte)(l >> (i*8) & 0xff);
			}
		}

		return dest;
	}
	public static final byte[] longToByte( long l) {
		byte[] dest = new byte[8];

		if(BYTE_ORDER == BYTE_ORDER_BIG_ENDIAN)
		{
			for(int i = 0; i < dest.length; i++)
			{
				dest[i] = (byte)(l >> ((dest.length - 1 - i) *8) & 0xff);
			}
		}
		else
		{
			for(int i = 0; i < dest.length; i++)
			{
				dest[i] = (byte)(l >> (i*8) & 0xff);
			}
		}

		return dest;
	}

	public static final byte[] long2bytes( long l, ByteOrder byteOrder) {
		byte[] dest = new byte[8];

		if(byteOrder == ByteOrder.BIG_ENDIAN)
		{
			for(int i = 0; i < dest.length; i++)
			{
				dest[i] = (byte)(l >> ((dest.length - 1 - i) *8) & 0xff);
			}
		}
		else
		{
			for(int i = 0; i < dest.length; i++)
			{
				dest[i] = (byte)(l >> (i*8) & 0xff);
			}
		}

		return dest;
	}
	public static final byte[] long2bytes( long l) {
		byte[] dest = new byte[8];

		if(BYTE_ORDER == BYTE_ORDER_BIG_ENDIAN)
		{
			for(int i = 0; i < dest.length; i++)
			{
				dest[i] = (byte)(l >> ((dest.length - 1 - i) *8) & 0xff);
			}
		}
		else
		{
			for(int i = 0; i < dest.length; i++)
			{
				dest[i] = (byte)(l >> (i*8) & 0xff);
			}
		}

		return dest;
	}
}
