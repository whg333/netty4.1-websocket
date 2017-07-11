package com.whg.util.string;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.util.Assert;

/**
 * 
 * 字符串工具类
 * 
 * @author luzj
 */
public class StringUtil {

	/** 默认的字符串数组组装成字符串的分隔符 */
	public final static String ELEMENT_SEPARATOR_COMMA = ",";
	
	public final static String ELEMENT_SEPARATOR_DOT = ".";
	
	public final static String ELEMENT_SEPARATOR_SEMICOLON = ":";
	
	public final static String ELEMENT_SEPARATOR_UNDERLINE = "_";

	private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	/**
	 * 使用,分隔的字符串中添加一个element
	 * 
	 * @param x
	 * @param element
	 * @return
	 */
	public static String addElement(String x, String element) {
		if (x == null || x.isEmpty()) {
			return element;
		}
		return new StringBuilder(x).append(ELEMENT_SEPARATOR_COMMA).append(element).toString();
	}

	/**
	 * 使用REG分隔的字符串remove掉其中一个element
	 * 
	 * @param element
	 * @param x
	 * @return
	 */
	public static String removeElement(String x, String element) {
		if (x == null) {
			return x;
		}
		if (x.equals(element)) {
			return "";
		}
		if (firstElement(x).equals(element)) {
			return x.substring(element.length() + 1);
		}
		if (lastElement(x).equals(element)) {
			return x.substring(0, x.length() - element.length() - 1);
		}
		return x.replace(ELEMENT_SEPARATOR_COMMA + element + ELEMENT_SEPARATOR_COMMA, ELEMENT_SEPARATOR_COMMA);
	}

	public static String removeFirst(String x) {
		int index = x.indexOf(ELEMENT_SEPARATOR_COMMA);
		if (index == -1) {
			return "";
		}
		return x.substring(index + 1);
	}

	/**
	 * 查找string中以REG分隔的元素的总个数
	 * 
	 * @param x
	 * @return
	 */
	public static int elementCount(String x) {
		if (isEmpty(x)) {
			return 0;
		}

		int count = 0;
		int index = 0;
		while (index != -1) {
			index = x.indexOf(ELEMENT_SEPARATOR_COMMA, index + 1);
			count++;
		}
		return count;
	}

	/**
	 * 是否存在
	 * 
	 * @param x
	 * @param element
	 * @return
	 */
	public static boolean existsElement(String x, String element) {
		if (x == null) {
			return false;
		}
		if (firstElement(x).equals(element)) {
			return true;
		}
		if (lastElement(x).equals(element)) {
			return true;
		}
		return x.indexOf(ELEMENT_SEPARATOR_COMMA + element + ELEMENT_SEPARATOR_COMMA) >= 0;
	}

	/**
	 * 以element_sepparator拆分
	 * 
	 * @param <T>
	 * @param x
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> split(String x, Class<T> clazz) {
		return split(x, clazz, ELEMENT_SEPARATOR_COMMA);
	}

	public static <T> List<T> split(String x, Class<T> clazz, String elementSeparator) {
		List<T> members = new ArrayList<T>();
		if (isEmpty(x)) {
			return members;
		}
		int beginIndex = 0;
		while (beginIndex != -1) {
			int endIndex = x.indexOf(elementSeparator, beginIndex);
			if (endIndex != -1) {
				members.add(valueOf(x.substring(beginIndex, endIndex), clazz));
				beginIndex = endIndex + 1;
			} else {
				members.add(valueOf(x.substring(beginIndex), clazz));
				beginIndex = -1;
			}
		}
		return members;
	}

	/**
	 * 以element_sepparator拆分
	 * 
	 * @param <T>
	 * @param x
	 * @param clazz
	 * @return
	 */
	public static int[] splitAsInt(String x) {
		if (isEmpty(x)) {
			return new int[0];
		}
		int[] members = new int[elementCount(x)];
		int beginIndex = 0;
		int i = 0;
		while (beginIndex != -1) {
			int endIndex = x.indexOf(ELEMENT_SEPARATOR_COMMA, beginIndex);
			int member;
			if (endIndex != -1) {
				member = Integer.parseInt(x.substring(beginIndex, endIndex));
				beginIndex = endIndex + 1;
			} else {
				member = Integer.parseInt(x.substring(beginIndex));
				beginIndex = -1;
			}
			members[i] = member;
			i++;
		}
		return members;
	}

	/**
	 * 获取以REG分隔的string中的第一个元素
	 * 
	 * @param x
	 * @return
	 */
	public static String firstElement(String x) {
		int index = x.indexOf(ELEMENT_SEPARATOR_COMMA);
		if (index == -1) {
			return x;
		}
		return x.substring(0, index);
	}

	/**
	 * 获取以REG分隔的string中的最后一个元素
	 * 
	 * @param x
	 * @return
	 */
	public static String lastElement(String x) {
		int index = x.lastIndexOf(ELEMENT_SEPARATOR_COMMA);
		if (index == -1) {
			return x;
		}
		return x.substring(index + 1);
	}

	private static Map<Class<?>, Method> valueOfMap = new HashMap<Class<?>, Method>();
	static {
		try {
			Method l = Long.class.getMethod("valueOf", String.class);
			valueOfMap.put(Long.class, l);
			valueOfMap.put(long.class, l);
			Method i = Integer.class.getMethod("valueOf", String.class);
			valueOfMap.put(Integer.class, i);
			valueOfMap.put(int.class, i);
			Method f = Float.class.getMethod("valueOf", String.class);
			valueOfMap.put(Float.class, f);
			valueOfMap.put(float.class, f);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 随机取元素 返回long类型list
	 * 
	 * @param randomNum
	 * @param exceptId
	 * @param members
	 * @return
	 */
	public static List<Long> randomMemberByLong(int randomNum, long exceptId, String members) {
		int maxTryCount = randomNum * 5;
		
		Random r = new Random();
		List<Long> randomMembers = new ArrayList<Long>();

		if (isEmpty(members)) {
			return randomMembers;
		}

		int membersSize = 0;
		int len = members.length();
		int tryCount = 0;
		if (len < 300) {
			int beginIndex = 0;
			while (tryCount++ < maxTryCount && membersSize < randomNum) {
				int endIndex = members.indexOf(ELEMENT_SEPARATOR_COMMA, beginIndex);
				if (endIndex == -1) {
					long randomMember = Long.parseLong(members.substring(beginIndex));
					if (randomMember != exceptId) {
						randomMembers.add(randomMember);
					}
					return randomMembers;
				}
				long randomMember = Long.parseLong(members.substring(beginIndex, endIndex));
				if (randomMember != exceptId) {
					randomMembers.add(randomMember);
					membersSize++;
				}
				beginIndex = endIndex + 1;
			}
		} else {
			while (tryCount++ < maxTryCount && membersSize < randomNum) {
				int beginIndex = r.nextInt(len);
				beginIndex = members.indexOf(ELEMENT_SEPARATOR_COMMA, beginIndex);
				if (beginIndex == -1) {
					continue;
				}
				int endIndex = members.indexOf(ELEMENT_SEPARATOR_COMMA, beginIndex + 1);
				if (endIndex == -1) {
					continue;
				}
				long randomMember = Long.parseLong(members.substring(beginIndex + 1, endIndex));
				if (randomMember != exceptId && !randomMembers.contains(randomMember)) {
					randomMembers.add(randomMember);
					membersSize++;
				}
			}
		}
		return randomMembers;
	}

	/**
	 * 把string转换成number 以及string类型
	 * 
	 * @param <T>
	 * @param value
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T valueOf(String value, Class<T> clazz) {
		if (value == null) {
			return null;
		}

		Method valueOfMethod = valueOfMap.get(clazz);
		if (valueOfMethod == null) {
			return (T) value;
		}

		try {
			return (T) valueOfMethod.invoke(null, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 判断一个字符串是否由0-9组成
	 * 
	 * @param x
	 * @return
	 */
	public static boolean isNumber(String x) {
		int len;
		if (x == null || (len = x.length()) == 0)
			return false;
		while (len-- > 0) {
			char temp = x.charAt(len);
			if (temp > 57 || temp < 48)
				return false;
		}
		return true;
	}

	/**
	 * 
	 * 一个汉字算两个字符
	 * 
	 * @param data
	 * @return
	 */
	public static int bytelength(String data) {
		try {
			return data.getBytes("GBK").length;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 使用md5算法加密字符串
	 * 
	 * @param x
	 * @return
	 */
	public static String encryptToMd5(String x) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(x.getBytes("UTF-8"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		byte[] byteArray = messageDigest.digest();
		StringBuilder md5SB = new StringBuilder();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5SB.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5SB.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5SB.toString();
	}

	/**
	 * 把0到9这10个数字按照配置的映射表映射成为另外的10个字符， 然后在把相邻两个字符交换一下位置，
	 * 如果字符串长度为奇数个，那么最后一个数字不参与字符交换处理。
	 * 假设映射表为9876543210，也就是说原来的0被映射为9，而原来的9被映射为0，中间以此类推。
	 * 而输入的用户id为10001，那么映射后的数据应该是89998。而经过交换处理后是98998。
	 * 
	 * @param number
	 * @return
	 */
	public static String encryptNumber(long number) {
		List<Integer> arr = convertNumberList(number);
		int[] newArr = convertByMapping(arr);
		swapPerTowElement(newArr);
		return arrayToString(newArr);
	}

	/**
	 * 每两个相邻数字之间交换顺序
	 * 
	 * @param arr
	 */
	private static void swapPerTowElement(int[] arr) {
		int len = arr.length;
		for (int i = 1; i < len; i += 2) {
			swap(arr, i, i - 1);
		}
	}

	private static void swap(int[] arr, int i, int j) {
		int t = arr[i];
		arr[i] = arr[j];
		arr[j] = t;
	}

	/**
	 * 把int[]转换成字符串
	 * 
	 * @param arr
	 * @return
	 */
	private static String arrayToString(int[] arr) {
		int len = arr.length;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			sb.append(arr[i]);
		}
		return sb.toString();
	}

	/**
	 * 0 --> 9 1 --> 8 ... 8 --> 1 9 --> 0 由于convertToList的时候把顺序弄反了 这里把顺序弄正确
	 * 
	 * @param arr
	 * @param len
	 * @return
	 */
	private static int[] convertByMapping(List<Integer> arr) {
		int len = arr.size();
		int[] newArr = new int[len];
		for (int i = len; i-- > 0;) {
			int n = arr.get(i);
			int m = 9 - n;
			newArr[len - 1 - i] = m;
		}
		return newArr;
	}

	/**
	 * 12345 --> {5,4,3,2,1}
	 * 
	 * @param number
	 * @return
	 */
	private static List<Integer> convertNumberList(long number) {
		List<Integer> arr = new ArrayList<Integer>();
		do {
			int t = (int) (number % 10);
			arr.add(t);
			number = number / 10;
		} while (number > 0);
		return arr;
	}

	/**
	 * 随机取元素
	 * 
	 * @param randomNum
	 * @param exceptMember
	 * @param members
	 * @return
	 */
	public static List<String> randomMembers(int randomNum, String exceptMember, String members) {
		int maxTryCount = randomNum * 5;
        List<String> randomMembers = new ArrayList<String>();
        if (isEmpty(members)) {
            return randomMembers;
        }

        int membersSize = 0;
        int len = members.length();
        
        int tryCount = 0;
        if (len < 300) {
            int beginIndex = 0;
            while (tryCount++ < maxTryCount && membersSize < randomNum) {
                int endIndex = members.indexOf(ELEMENT_SEPARATOR_COMMA, beginIndex);
                if (endIndex == -1) {
                    String randomMember = members.substring(beginIndex);
                    if (!randomMember.equals(exceptMember)) {
                        randomMembers.add(randomMember);
                    }
                    return randomMembers;
                }
                String randomMember = members.substring(beginIndex, endIndex);
                if (!randomMember.equals(exceptMember)) {
                    randomMembers.add(randomMember);
                    membersSize++;
                }
                beginIndex = endIndex + 1;
            }
        } else {
            Random r = new Random();
            while (tryCount++ < maxTryCount && membersSize < randomNum) {
                int beginIndex = r.nextInt(len);
                beginIndex = members.indexOf(ELEMENT_SEPARATOR_COMMA, beginIndex);
                if (beginIndex == -1) {
                    continue;
                }
                int endIndex = members.indexOf(ELEMENT_SEPARATOR_COMMA, beginIndex + 1);
                if (endIndex == -1) {
                    continue;
                }
                String randomMember = members.substring(beginIndex + 1, endIndex);
                if (!randomMember.equals(exceptMember) && !randomMembers.contains(randomMember)) {
                    randomMembers.add(randomMember);
                    membersSize++;
                }
            }
        }
        return randomMembers;
	}

	/**
	 * 获取字符串开头的几个元素
	 * 
	 * @param num
	 * @param exceptMember
	 * @param members
	 * @return
	 */
	public static List<String> getFistMembers(int num, String exceptMember, String members) {
		List<String> rMembers = new ArrayList<String>();

		int beginIndex = 0;
		int membersSize = 0;
		while (membersSize < num) {
			int endIndex = members.indexOf(ELEMENT_SEPARATOR_COMMA, beginIndex);
			if (endIndex == -1) {
				String randomMember = members.substring(beginIndex);
				if (randomMember != exceptMember) {
					rMembers.add(randomMember);
				}
				return rMembers;
			}
			String randomMember = members.substring(beginIndex, endIndex);
			if (randomMember != exceptMember) {
				rMembers.add(randomMember);
				membersSize++;
			}
			beginIndex = endIndex + 1;
		}
		return rMembers;
	}

	/**
	 * 判断字符串是否为空 null or "" return true; "  " return true; " pop" return false;
	 * "  pop  " return false;
	 * 
	 * @param x
	 * @return
	 */
	public static boolean isEmpty(String x) {
		int len;
		if (x == null || (len = x.length()) == 0)
			return true;

		while (len-- > 0) {
			if (!Character.isWhitespace(x.charAt(len)))
				return false;
		}

		return true;
	}

	/**
	 * trim
	 * 
	 * @param x
	 * @return
	 */
	public static String trim(String x) {
		if (x == null)
			return null;

		return x.trim();
	}

	/**
	 * 使用md5算法加密字符串
	 * 
	 * @param x
	 * @return
	 */
	public static String encrypt(String x) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(x.getBytes("UTF-8"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		byte[] byteArray = messageDigest.digest();
		StringBuilder md5SB = new StringBuilder();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5SB.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5SB.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5SB.toString();
	}

	/**
	 * 算法名称 hmacsha1
	 */
	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

	/**
	 * hmacsha256
	 */
	private static String HMAC_SHA256_ALGORITHM = "HMACSHA256";;

	/**
	 * 使用hmacSHA1加密并转换成16位字符串
	 * 
	 * @param key
	 * @param data
	 * @return
	 */
	public static String hmacSHA1(String key, String data) {
		BigInteger hash = new BigInteger(1, hmacSHA1ToBytes(key, data));

		String hmac = hash.toString(16);
		if (hmac.length() % 2 != 0) {
			hmac = "0" + hmac;
		}
		return hmac;
	}

	/**
	 * 使用hmacSHA1算法加密
	 * 
	 * @param key
	 * @param data
	 * @return
	 */
	public static byte[] hmacSHA1ToBytes(String key, String data) {
		try {
			// Get an hmac_sha1 key from the raw key bytes
			byte[] keyBytes = key.getBytes();
			SecretKeySpec signingKey = new SecretKeySpec(keyBytes, HMAC_SHA1_ALGORITHM);

			// Get an hmac_sha1 Mac instance and initialize with the signing key
			Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
			mac.init(signingKey);

			return mac.doFinal(data.getBytes());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param key
	 * @param data
	 * @return
	 */
	public static String hmacsha256(String key, String data) {
		try {
			return new String(Base64.encodeBase64(hmacsha256ToBytes(key, data)), DEFAULT_CHARSET);
		} catch (Exception e) {
			return null;
		}
	}

	public static String decodeBase64Url(String context) {
		context = fixPaddingBase64String(fixBase64String(context));
		return new String(Base64.decodeBase64(context.getBytes()));
	}

	private static String fixPaddingBase64String(String base64) {
		int paddingLength = base64.length() % 4 == 0 ? 0 : 4 - base64.length() % 4;
		for (int i = 0; i < paddingLength; i++) {
			base64 += "=";
		}
		return base64;
	}

	private static String fixBase64String(String context) {
		return context.replaceAll("-", "+").replaceAll("_", "/");
	}

	public static String encodeBase64Url(String context) {
		return new String(Base64.encodeBase64(context.getBytes())).replaceAll("\\+", "-").replaceAll("/", "_")
				.replaceAll("=", "");
	}

	public static String encodeBase64(String txt) {
		return new String(Base64.encodeBase64(txt.getBytes()));
	}

	public static String decodeBase64(String txt) {
		return new String(Base64.decodeBase64(txt.getBytes()));
	}

	/**
	 * @param key
	 * @param data
	 * @return
	 */
	public static byte[] hmacsha256ToBytes(String key, String data) {
		try {
			SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), HMAC_SHA256_ALGORITHM);
			Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
			mac.init(secretKeySpec);
			return mac.doFinal(data.getBytes());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 使用hmacSHA1算法加密 并将加密后的内容转换成base64编码
	 * 
	 * @param key
	 * @param data
	 * @return
	 */
	public static String hmacSHA1ToBase64(String key, String data) {
		byte[] encodeBase64 = Base64.encodeBase64(hmacSHA1ToBytes(key, data));
		return new String(encodeBase64, DEFAULT_CHARSET);
	}

	
	public static List<Integer> str2IntList(String str) {
		if(isEmpty(str)){
			return new ArrayList<Integer>();
		}
		String[] formationStrs = str.split(",");
		List<Integer> formation = new ArrayList<Integer>(formationStrs.length);
		for (String s : formationStrs) {
			formation.add(Integer.parseInt(s.trim()));
		}
		return formation;
	}
	
	public static String intList2str(List<Integer> intList) {
		StringBuilder sb = new StringBuilder();
		int size = intList.size();
		for(int i=0;i<size;i++){
			int value = intList.get(i);
			sb.append(value);
			if(i != size-1){
				sb.append(",");
			}
		}
		return sb.toString();
	}
	
	public static List<Long> str2LongList(String str) {
		if(isEmpty(str)){
			return new ArrayList<Long>();
		}
		String[] formationStrs = str.split(",");
		List<Long> formation = new ArrayList<Long>(formationStrs.length);
		for (String s : formationStrs) {
			formation.add(Long.parseLong(s.trim()));
		}
		return formation;
	}
	
	public static byte[] encode(String str){
		Assert.notNull(str);
		return str.getBytes(DEFAULT_CHARSET);
	}
	
	public static String encode(byte[] bytes){
		Assert.notNull(bytes);
		return new String(bytes, DEFAULT_CHARSET);
	}

}
