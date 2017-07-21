package trace.mycat;

import java.io.File;

public class TarGzUtils {
	private static final String TAREXT = ".tar";
	private static final String GZEXT = ".gz";

	/**
	 * 压缩文件并删除源文件
	 * 
	 * @param path
	 *            文件路径
	 * @throws Exception
	 */
	public static void compress(String srcPath) throws Exception {
		File file = new File(srcPath);
		compress(file);
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
		// 归档后路径
		String destPath = file.getAbsolutePath() + TAREXT;
		// 先归档
		TarUtils.archive(file);
		// 后压缩
		GZipUtils.compress(destPath, true);
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
		File file = new File(inputFileName);
		// 归档后路径
		String destPath = file.getAbsolutePath() + TAREXT;
		// 先归档
		TarUtils.archive(file);
		File tmpFile = new File(destPath);
		// 后压缩
		GZipUtils.compress(destPath, outputFileName);
		// 删除临时文件
		tmpFile.delete();
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
		String destFile = file.getAbsolutePath().replace(GZEXT, "");
		// 先解压
		GZipUtils.decompress(file, delete);
		File tmpFile = new File(destFile);
		// 后解归档
		TarUtils.dearchive(tmpFile);
		// 删除临时文件
		tmpFile.delete();
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
		File file = new File(inputFileName);
		String destFile = file.getAbsolutePath().replace(GZEXT, "");
		// 先解压
		GZipUtils.decompress(file, false);
		File tmpFile = new File(destFile);
		// 后解归档
		TarUtils.dearchive(destFile, outputFileName);
		// 删除临时文件
		tmpFile.delete();
	}

	public static void main(String[] args) {
		try {
			String src="D:\\out\\test.txt";
			String compress="D:\\out\\compress\\test.tar.gz";
			TarGzUtils.compress(src,compress);
			TarGzUtils.decompress(compress,"D:\\out\\decompress");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
