package trace.mycat;

/**
 * 2010-4-13
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * GZIP工具
 * 
 * @author
 * @since 1.0
 */
public abstract class GZipUtils {

	public static final int BUFFER = 1024;
	public static final String EXT = ".gz";

	/**
	 * 数据压缩
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] compress(byte[] data) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 压缩
		compress(bais, baos);
		byte[] output = baos.toByteArray();
		baos.flush();
		baos.close();
		bais.close();
		return output;
	}

	/**
	 * 数据压缩
	 * 
	 * @param is
	 * @param os
	 * @throws Exception
	 */
	public static void compress(InputStream is, OutputStream os) throws Exception {
		GZIPOutputStream gos = new GZIPOutputStream(os);
		int count;
		byte data[] = new byte[BUFFER];
		while ((count = is.read(data, 0, BUFFER)) != -1) {
			gos.write(data, 0, count);
		}
		gos.finish();
		gos.flush();
		gos.close();
	}

	/**
	 * 压缩文件并删除源文件
	 * 
	 * @param path
	 *            文件路径
	 * @throws Exception
	 */
	public static void compress(String path) throws Exception {
		compress(path, true);
	}

	/**
	 * 压缩文件并删除源文件
	 * 
	 * @param file
	 *            文件对象
	 * @throws Exception
	 */
	public static void compress(File file) throws Exception {
		compress(file, true);
	}

	/**
	 * 压缩文件
	 * 
	 * @param path
	 *            文件路径
	 * @param delete
	 *            是否删除原始文件
	 * @throws Exception
	 */
	public static void compress(String path, boolean delete) throws Exception {
		File file = new File(path);
		compress(file, delete);
	}

	/**
	 * 压缩文件
	 * 
	 * @param file
	 *            文件对象
	 * @param delete
	 *            是否删除原始文件
	 * @throws Exception
	 */
	public static void compress(File file, boolean delete) throws Exception {
		FileInputStream fis = new FileInputStream(file);
		FileOutputStream fos = new FileOutputStream(file.getAbsolutePath() + EXT);
		compress(fis, fos);
		fis.close();
		fos.flush();
		fos.close();
		if (delete) {
			file.delete();
		}
	}

	/**
	 * 压缩文件到指定目录
	 * 
	 * @param inputFileName
	 *            源文件
	 * @param outputFileName
	 *            目标文件\文件夹
	 * @throws Exception
	 */
	public static void compress(String inputFileName, String outputFileName) throws Exception {
		File srcFile = new File(inputFileName);
		File destFile = new File(outputFileName);
		if (destFile.isDirectory()) {
			// 如果目标是目录则压缩后文件名为源文件名加上.gz 后缀
			destFile = new File(destFile.getAbsolutePath() + File.separator + srcFile.getName() + EXT);
		}
		// 文件检查
		fileProber(destFile);
		
		FileInputStream inputFile = new FileInputStream(srcFile);
		FileOutputStream outputFile = new FileOutputStream(destFile);
		compress(inputFile, outputFile);
		inputFile.close();
		outputFile.flush();
		outputFile.close();
	}

	/**
	 * 数据解压缩
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] decompress(byte[] data) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 解压缩
		decompress(bais, baos);
		data = baos.toByteArray();
		baos.flush();
		baos.close();
		bais.close();
		return data;
	}

	/**
	 * 数据解压缩
	 * 
	 * @param is
	 * @param os
	 * @throws Exception
	 */
	public static void decompress(InputStream is, OutputStream os) throws Exception {
		GZIPInputStream gis = new GZIPInputStream(is);
		int count;
		byte data[] = new byte[BUFFER];
		while ((count = gis.read(data, 0, BUFFER)) != -1) {
			os.write(data, 0, count);
		}
		gis.close();
	}

	/**
	 * 解压文件并删除源文件
	 * 
	 * @param path
	 *            文件路径
	 * @throws Exception
	 */
	public static void decompress(String path) throws Exception {
		decompress(path, true);
	}

	/**
	 * 解压文件并删除源文件
	 * 
	 * @param file
	 *            文件对象
	 * @throws Exception
	 */
	public static void decompress(File file) throws Exception {
		decompress(file, true);
	}

	/**
	 * 解压文件
	 * 
	 * @param path
	 *            文件路径
	 * @param delete
	 *            是否删除原始文件
	 * @throws Exception
	 */
	public static void decompress(String path, boolean delete) throws Exception {
		File file = new File(path);
		decompress(file, delete);
	}

	/**
	 * 解压文件
	 * 
	 * @param file
	 *            文件对象
	 * @param delete
	 *            是否删除原始文件
	 * @throws Exception
	 */
	public static void decompress(File file, boolean delete) throws Exception {
		FileInputStream fis = new FileInputStream(file);
		FileOutputStream fos = new FileOutputStream(file.getAbsolutePath().replace(EXT, ""));
		decompress(fis, fos);
		fis.close();
		fos.flush();
		fos.close();

		if (delete) {
			file.delete();
		}
	}

	/**
	 * 解压文件到指定目录
	 * 
	 * @param inputFileName
	 *            源文件
	 * @param outputFileName
	 *            目标文件夹
	 * @throws Exception
	 */
	public static void decompress(String inputFileName, String outputFileName) throws Exception {
		//File srcFile = new File(inputFileName);
		File destFile = new File(outputFileName);
		fileProber(destFile);
		FileInputStream inputFile = new FileInputStream(inputFileName);
		FileOutputStream outputFile = new FileOutputStream(outputFileName);
		decompress(inputFile, outputFile);
		inputFile.close();
		outputFile.flush();
		outputFile.close();
	}

	/**
	 * 文件探针
	 * 
	 * <pre>
	 * 当父目录不存在时，创建目录！
	 * </pre>
	 * 
	 * @param dirFile
	 */
	private static void fileProber(File dirFile) {
		File parentFile = dirFile.getParentFile();
		if (!parentFile.exists()) {
			// 递归寻找上级目录
			fileProber(parentFile);
			parentFile.mkdir();
		}
	}
}
