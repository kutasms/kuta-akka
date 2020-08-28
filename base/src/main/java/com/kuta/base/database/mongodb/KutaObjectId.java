package com.kuta.base.database.mongodb;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Random;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import com.kuta.base.util.KutaByteUtil;

/**
 * <p>KSF自定义Mongodb数据库ObjectId对象</p>
 * <p>12字节_id字段数据 [0-5]字节 创建时间 [6-7] 机器码 [8-9] PID [10-11] 随机码</p>
 * */
public class KutaObjectId {
	/**
	 * 进程编号二进制值
	 * */
	private static final byte[] pid;
	/**
	 * 随机数生成器
	 * */
	private static final Random random = new Random();
	/**
	 * 机器编号二进制值
	 * */
	private static byte[] machine;
	/**
	 * 日期增加数
	 * */
	public static long INCREMENT = 20000000000000L;
	/**
	 * 开辟12字节的内存空间
	 * */
	private ByteBuffer buff = ByteBuffer.allocate(12);
	/**
	 * String类型的值
	 * */
	private String stringVal;
	/**
	 * byte类型的值
	 * */
	private byte[] byteVal;
	/**
	 * 生成ID的时间长整型刻度数
	 * */
	private long time;
	/**
	 * 机器编号
	 * */
	private Integer machineId;
	/**
	 * 进程编号
	 * */
	private Integer processId;
	
	static {
		String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
        String processID = processName.substring(0,processName.indexOf('@'));
        Integer process = Integer.parseInt(processID);
        pid = KutaByteUtil.intHex(process, 2);
        InetAddress addr;
        try {
			addr = InetAddress.getLocalHost();
			machine = KutaByteUtil.intHex(addr.getHostName().hashCode(), 2);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	/**
	 * 生成12个字节的_id字符串(16进制)
	 * @author rhina
	 * @return 生成的_id字符串
	 * */
	public static String generateId() {
	
		ByteBuffer buffer = ByteBuffer.allocate(12);
		long current = System.currentTimeMillis();
		current += INCREMENT;
		buffer.put(KutaByteUtil.longHex(current, 6));
		byte[] seed = new byte[2];
		random.nextBytes(seed);
		buffer.put(machine);
		buffer.put(pid);
		buffer.put(seed);
		buffer.flip();
		return KutaByteUtil.bytesToHexString(buffer.array());
	}
	
	/**
	 * 将一个id字符串解析成KutaObjectId对象
	 * @param id id字符串
	 * @return KSFObjectId对象
	 * @see KutaObjectId
	 * @throws DecoderException 解码异常
	 * */
	public static KutaObjectId parse(String id) throws DecoderException {
		return new KutaObjectId(id);
	}
	
	/**
	 * 构造函数
	 * @param id KSFObjectId的字符串值
	 * */
	public KutaObjectId(String id) throws DecoderException {
		this.stringVal = id;
		this.byteVal = Hex.decodeHex(this.stringVal);
		this.buff.put(byteVal);
		this.buff.flip();
		byte[] timeDst = new byte[6];
		this.buff.get(timeDst, 0, 6);
		this.time = KutaByteUtil.bytesToLong(timeDst) - INCREMENT;
		this.machineId = Short.toUnsignedInt(this.buff.getShort());
		this.processId = Short.toUnsignedInt(this.buff.getShort());
	}
	/**
	 * 构造函数
	 * */
	public KutaObjectId()  {
		this.stringVal = generateId();
		try {
			this.byteVal = Hex.decodeHex(this.stringVal);
			this.buff.put(byteVal);
			this.buff.flip();
			byte[] timeDst = new byte[6];
			this.buff.get(timeDst, 0, 6);
			this.time = KutaByteUtil.bytesToLong(timeDst) - INCREMENT;
			this.machineId = Short.toUnsignedInt(this.buff.getShort());
			this.processId = Short.toUnsignedInt(this.buff.getShort());
		} catch (DecoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * <p>获取数据创建时间，精确到毫秒</p>
	 * @return 时间刻度数
	 * */
	public Long getTime() {
		return this.time;
	}
	
	/**
	 * 获取数据创建时间
	 * @return Date
	 * @see java.util.Date
	 * */
	public Date getDate() {
		return new Date(this.time);
	}

	/**
	 * 获取_id字符串
	 * @return id的字符串值
	 * */
	public String getStringVal() {
		return stringVal;
	}

	/**
	 * 获取_id的byte[]
	 * @return id的byte值
	 * */
	public byte[] getByteVal() {
		return byteVal;
	}

	/**
	 * 获取机器码
	 * @return 机器编号
	 * */
	public Integer getMachineId() {
		return machineId;
	}

	/**
	 * 获取进程编号
	 * @return 进程编号
	 * */
	public Integer getProcessId() {
		return processId;
	}


}
