package com.market.mall.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

public class BeanUtils {

	static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * ���������ת�����õ�bean�ĸ����ֶ���
	 * @param request
	 * @param t
	 * @return
	 */
	public static <T> T toBean(ServletRequest request, T t) {
		//�����ֵ����tΪ�����˳�
		if (t == null)
			return t;
		for (Field f : getAllFields(t.getClass())) {
			//��ȡ����ֵ
			String svalue = request.getParameter(f.getName());
			//��ֵ����
			if (svalue == null)
				continue;
			try {
				//����ת�����ֵ����
				Object ovalue = cast(svalue, f.getType());
				f.setAccessible(true);
				f.set(t, ovalue);
			} catch (Exception e) {
				System.err.println("��������������󣺲�����=" + f.getName() + "���ֶ�����=" + f.getType() + "������ֵ=" + svalue);
			}
		}

		return t;

	}

	public static <T> T asBean(ServletRequest request, Class<T> cls) {
		try {
			return toBean(request, cls.newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public static Map<Object, Object> asMap(Object... objs) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		for (int i = 0; i < objs.length / 2; i++) {
			map.put(objs[i * 2], objs[i * 2 + 1]);
		}
		return map;
	}

	/**
	 * ���ַ���ת��ָ�������ͣ���� ����ֵΪ�գ�����ת�����̳����쳣���򷵻�Ĭ��ֵ
	 * @param svalue
	 * @param cls
	 * @param defaultValue Ĭ��ֵ
	 * @return
	 */
	public static <T> T cast(String svalue, Class<T> cls, T defaultValue) {
		if (svalue == null) {
			return defaultValue;
		} else {
			try {
				T ret = cast(svalue, cls);
				return ret == null ? defaultValue : ret;
			} catch (Exception e) {
				System.err.println(e.getMessage());
				return defaultValue;
			}
		}
	}

	@SuppressWarnings("unchecked")
	/**
	 * ������ֵת��ָ��������
	 * @param svalue
	 * @param cls
	 * @return
	 */
	public static <T> T cast(Object value, Class<T> cls) {
		if (value == null) {
			return null;
		} else if (cls.isAssignableFrom(value.getClass())) {
			return (T) value;
		} else {
			switch (cls.getName()) {
			case "java.lang.String":
				return (T) value.toString();
			case "int":
			case "java.lang.Integer":
				return (T) Integer.valueOf("" + value);
			case "byte":
			case "java.lang.Byte":
				return (T) Byte.valueOf("" + value);
			case "short":
			case "java.lang.Short":
				return (T) Short.valueOf("" + value);
			case "long":
			case "java.lang.Long":
				return (T) Long.valueOf("" + value);
			case "float":
			case "java.lang.Float":
				return (T) Float.valueOf("" + value);
			case "double":
			case "java.lang.Double":
				return (T) Double.valueOf("" + value);
			case "boolean":
			case "java.lang.Boolean":
				return (T) Boolean.valueOf("" + value);
			case "char":
			case "java.lang.Character":
				String s = "" + value;
				return (T) Character.valueOf(s.length() == 0 ? null : s.charAt(0));
			case "java.sql.Date": // ע�⣺�������������и�ʽ������
				return (T) java.sql.Date.valueOf("" + value);
			case "java.sql.Timestamp": // ע�⣺�������������и�ʽ������
				return (T) java.sql.Timestamp.valueOf("" + value);
			default:
				return (T) value;
			}
		}
	}

	public static boolean canCast(Class<?> cls) {
		switch (cls.getName()) {
		case "java.lang.String":
		case "int":
		case "java.lang.Integer":
		case "byte":
		case "java.lang.Byte":
		case "short":
		case "java.lang.Short":
		case "long":
		case "java.lang.Long":
		case "float":
		case "java.lang.Float":
		case "double":
		case "java.lang.Double":
		case "boolean":
		case "java.lang.Boolean":
		case "char":
		case "java.lang.Character":
		case "java.sql.Date":
		case "java.sql.Timestamp":
			return true;
		}
		return false;
	}

	/**
	 * ���ݷ���������ƥ��ķ�������
	 * @param cls
	 * @param methodName
	 * @return
	 */
	public static Method[] getMethodsByName(Class<?> cls, String methodName) {
		ArrayList<Method> list = new ArrayList<Method>();
		for (Method m : getAllMethods(cls)) {
			if (m.getName().equals(methodName)) {
				m.setAccessible(true);
				list.add(m);
			}
		}
		return list.toArray(new Method[list.size()]);

	}

	/**
	 * ���ص�һ����Ϊ�յ�ֵ
	 * @param values
	 * @return
	 */
	public static Object notNull(Object... values) {
		for (Object o : values) {
			if (o != null) {
				return o;
			}
		}
		return null;
	}

	/**
	 * �Ӷ����л�ȡһ���ֶ�ֵ
	 * @param bean		����
	 * @param fieldName	�ֶ���
	 * @return			�ֶ�ֵ
	 */
	public static Object getValue(Object bean, String fieldName) {
		Class<?> cls = bean.getClass();
		try {
			// ��ȡ�ֶ�
			Field field = cls.getDeclaredField(fieldName);
			// �����ֶο���ֱ�ӷ���
			field.setAccessible(true);
			// �����ֶ�ֵ
			return field.get(bean);
		} catch (NoSuchFieldException | SecurityException e) {
			System.out.printf("%sû������ֶΣ�%s", cls.getName(), fieldName);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			System.out.printf("%s�޷���ȡ���ֶ�ֵ��%s", cls.getName(), fieldName);
		}
		return null;
	}

	/**
	 * �Ӷ����л�ȡһ���ֶ�ֵ
	 * @param bean		����
	 * @param fieldName	�ֶ���
	 * @return			�ֶ�ֵ
	 */
	public static void setValue(Object bean, String fieldName, Object value) {
		Class<?> cls = bean.getClass();
		try {
			// ��ȡ�ֶ�
			Field field = cls.getDeclaredField(fieldName);
			// �����ֶο���ֱ�ӷ���
			field.setAccessible(true);
			// �����ֶ�ֵ
			field.set(bean, value);
		} catch (NoSuchFieldException | SecurityException e) {
			System.out.printf("%sû������ֶΣ�%s", cls.getName(), fieldName);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			System.out.printf("%s�޷����ø��ֶ�ֵ��%s", cls.getName(), fieldName);
		}
	}

	/**
	 * ����һ�����󣬸ö�������ǣ����顢���ϡ�Map��ʵ�����
	 * @param items
	 * @param fields
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Iterable<Object> each(final Object items, Object... fields) {
		if (items instanceof Collection) {
			return ((Collection) items);
		}

		int tempCount;
		if (items instanceof Object[]) {
			tempCount = ((Object[]) items).length;
		} else if (fields.length == 1 && fields[0] != null && fields[0] instanceof Collection) {
			fields = ((Collection) fields[0]).toArray();
			tempCount = fields.length;
		} else {
			tempCount = fields.length;
		}

		final int _count = tempCount;
		final Object[] _fields = fields;

		/**
		 * �� items ���е���
		 */
		return new Iterable<Object>() {
			@Override
			public Iterator<Object> iterator() {
				return new Iterator<Object>() {
					int i = 0;

					@Override
					public boolean hasNext() {
						return i < _count;
					}

					@Override
					public Object next() {
						Object ret;
						if (items instanceof Object[]) {
							ret = ((Object[]) items)[i];
						} else if (items instanceof Map) {
							ret = ((Map) items).get(_fields[i]);
						} else {
							ret = getValue(items, "" + _fields[i]);
						}
						i++;
						return ret;
					}
				};
			}

		};
	}

	@SuppressWarnings("unchecked")
	public static <T> boolean contains(T obj, T... objs) {
		for (T o : objs) {
			if (obj == null) {
				if (o == null) {
					return true;
				}
			} else {
				if (obj.equals(o)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static Field getField(Class<?> cls, String fieldName) {
		for(Field f : getAllFields(cls)) {
			if(fieldName.equals(f.getName())) {
				return f;
			}
		}
		return null;
	}

	public static List<Field> getAllFields(Class<?> cls) {
		ArrayList<Field> fs = new ArrayList<Field>();
		getAllFields(cls, fs);
		return fs;
	}

	private static void getAllFields(Class<?> cls, ArrayList<Field> fs) {
		fs.addAll(Arrays.asList(cls.getDeclaredFields()));
		cls = cls.getSuperclass();
		if (cls != null) {
			getAllFields(cls, fs);
		}
	}

	public static List<Method> getAllMethods(Class<?> cls) {
		ArrayList<Method> ms = new ArrayList<Method>();
		getAllMethods(cls, ms);
		return ms;
	}

	private static void getAllMethods(Class<?> cls, ArrayList<Method> ms) {
		ms.addAll(Arrays.asList(cls.getDeclaredMethods()));
		cls = cls.getSuperclass();
		if (cls != null) {
			getAllMethods(cls, ms);
		}
	}

}
