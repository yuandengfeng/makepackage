package FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;


//此类是sftp文件上传，http方式上传见webtest项目中的package servlet.FileUtil

public class SftpUtil {
	private final static Logger logger = Logger.getLogger(SftpUtil.class);
    /**
     * 配置：远程地址
     */
//    public static final String CONFIG_REMOTE_PATH = ConfigProperties.getProperties("SftpServer.remotePath");

	private String host;

	private String username;

	private String password;
	
	private String privateKey;
	

	private int port = 22;

	private ChannelSftp sftp = null;

	private final String seperator = "/";

	private Session sshSession = null;

	public SftpUtil(String host, int port, String username, String password) {
		super();
		this.host = host;
		this.username = username;
		this.password = password;
		this.port = port;
	} 
	public SftpUtil(String host, int port, String username, String password,String privateKey) {
		super();
		this.host = host;
		this.username = username;
		this.password = password;
		this.port = port;
		this.privateKey = privateKey;
	}

	/**
	 * connect server via sftp
	 * @throws Exception 
	 */
	public void connect() throws Exception {
		try {
			if (sftp != null) {
				logger.info("SFTP已经连接，不能重复建立");
			}
			JSch jsch = new JSch();
			if(privateKey!=null&&privateKey.length()>0){
				logger.info("加载免密码私钥认证，证书");
				jsch.addIdentity(privateKey);//免密码私钥认证，证书目录
			}
			
			jsch.getSession(username, host, port);
			sshSession = jsch.getSession(username, host, port);
			
			logger.info("正在创建SFTP连接Session");
			
			if(password!=null&&password.length()>0){
				sshSession.setPassword(password);
			}
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			logger.info("成功创建SFTP连接Session");
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			logger.info("已经连接到SFTP服务器: " + host);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * Disconnect with server
	 */
	public void disconnect() {
		if (this.sftp != null) {
			if (this.sftp.isConnected()) {
				this.sftp.disconnect();
				logger.info("已关闭SFTP连接");
			} else if (this.sftp.isClosed()) {
				logger.info("sftp 连接已经关闭");
			}
		}
		if (sshSession != null) {
			if (this.sshSession.isConnected()) {
				this.sshSession.disconnect();
				logger.info("已关闭SFTP sshSession");
			} else if (this.sftp.isClosed()) {
				logger.info("sftp sshSession 已经关闭");
			}
		}

	}

	public void download() {
		// TODO Auto-generated method stub

	}

	public void download(String directory, String downloadFile,String saveFile) {
			
		try {
			sftp.cd(directory);
			File file = new File(saveFile);
			//将目标服务器上文件名为downloadFile的文件下载到本地，下载的数据写入到输出流对象new FileOutputStream(file)（如：文件输出流）。采用默认的传输模式：OVERWRITE
			sftp.get(downloadFile, new FileOutputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    /**
     * 下载远程sftp服务器文件
     * @param remotePath 
     * @param remoteFilename  服务器文件名
     * @param localFilename 本地文件
     * @return
     */ 
    public  boolean downloadFile(String remotePath,String remoteFilename,String localFilename){
        FileOutputStream output = null; 
        boolean success = false; 
        try { 
            if (null != remotePath && remotePath.trim() != "") { 
                sftp.cd(remotePath); 
            } 
  
            File localFile = new File(localFilename.replace(".ok", ".dat")); 
            //有文件和下载文件重名 
            if (localFile.exists()) { 
                logger.error("文件: " + localFilename + " 已经存在!");
                return success; 
            } 
            
            if(".ok".equals(remoteFilename.substring(remoteFilename.lastIndexOf(".ok"), remoteFilename.length()))){
            	localFile = new File(localFilename.replace(".ok", ".dat"));
            	 output = new FileOutputStream(localFile); 
            	String remoteName= remoteFilename.replace(".ok", ".dat");
                 sftp.get(remoteName, output); 
                 success = true; 
                 logger.info("成功接收文件,本地路径："+localFilename);
            }else{
            	 logger.error("文件: " + localFilename + " 未生成.ok文件!");
            }
           
        } catch (SftpException e) { 
            System.err.println("接收文件时有SftpException异常!"); 
            System.err.println(e.getMessage()); 
            return success; 
        } catch (IOException e) { 
            System.err.println("接收文件时有I/O异常!"); 
            System.err.println(e.getMessage()); 
            e.printStackTrace();
            return success; 
        } finally { 
            try { 
                if (null != output) { 
                    output.close(); 
                } 
                //关闭连接 
                disconnect(); 
            } catch (IOException e) { 
                System.err.println("关闭文件时出错!"); 
                System.err.println(e.getMessage()); 
            } 
        } 
        return success; 
    }  
	/**
	 * upload all the files to the server
	 * @throws Exception 
	 */
	public void upload(String localPath, String remotePath, String localBasePath) throws Exception {
		// List<String> fileList = this.getFileEntryList(fileListPath);
	    try {
//    	    logger.info("开始上传本地文件或文件夹:" + localPath);
    		List fileList = new ArrayList();
    		listFile(new File(localPath), fileList);
		
			if (fileList != null) {
				for (int index = 0; index < fileList.size(); index++) {
					String localFile = (String) fileList.get(index);
					File file = new File(localFile);

					if (file.isFile()) {
//						logger.info("localFile : " + file.getAbsolutePath());

						String remoteFile = remotePath + this.seperator
								+ localFile.substring(localBasePath.length());
//						logger.info("SFTP远程路径:" + remoteFile);
						File rfile = new File(remoteFile);
						String rpath = rfile.getParent();
						try {
							createDir(rpath, sftp);
						} catch (Exception e) {
							logger.error("*******create path failed",e);
							throw e;
						}

						this.sftp
								.put(new FileInputStream(file), file.getName());
//						logger.debug("已经上传本地文件： " + localFile);
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
			throw e;
		} catch (SftpException e) {
			// TODO Auto-generated catch block
		    logger.error(e.getMessage(),e);
			throw e;
		} catch(Exception e){
            logger.error(e.getMessage(),e);
            throw e;
        }

	}
	
	  /**
		 * upload all the files to the server
		 * @throws Exception 
		 */
	    public void upload1(String localPath, String remotePath, String localBasePath) throws Exception {
			// List<String> fileList = this.getFileEntryList(fileListPath);
	    	File file =null;
		    try {
//	    	    logger.info("开始上传本地文件或文件夹:" + localPath);
	    		List fileList = new ArrayList();
	    		listFile(new File(localPath), fileList);
				if (fileList != null) {
					for (int index = 0; index < fileList.size(); index++) {
						String localFile = (String) fileList.get(index);
						 file = new File(localFile);

						if (file.isFile()) {
//							logger.info("localFile : " + file.getAbsolutePath());
//							logger.info("SFTP远程路径:" + localBasePath);
						//	File rfile = new File(localBasePath);
						//	String rpath = rfile.getParent();
							try {
								createDir(localBasePath, sftp);
							} catch (Exception e) {
								logger.error("*******create path failed",e);
								throw e;
							}

							this.sftp.put(new FileInputStream(file), file.getName());
									
//							logger.debug("已经上传本地文件： " + localFile);
						}
					}
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(),e);
				throw e;
			} catch (SftpException e) {
				// TODO Auto-generated catch block
			    logger.error("localFile : " + file.getAbsolutePath()+"\nSFTP远程路径:" + localBasePath+"\n"+e.getMessage(),e);
				throw e;
			} catch(Exception e){
	            logger.error(e.getMessage(),e);
	            throw e;
	        }

		}

	/**
	 * localFile,localFileName, remoteFile,remoteFileJ)
	 * upload all the files to the server
	 * @throws Exception 
	 */
	public void upload(String localFile, String remoteFile) throws Exception {
		// List<String> fileList = this.getFileEntryList(fileListPath);
		logger.debug("开始上传");
		try {
			File file = new File(localFile);

			if (file.isFile()) {
				logger.debug("localFile : " + file.getAbsolutePath());

				logger.debug("SFTP远程路径:" + remoteFile);
				File rfile = new File(remoteFile);
				String rpath = rfile.getParent();//\var\www\html\TopMall\goodsId\AAAAVVV
				try {
					int endIndex = localFile.lastIndexOf("/");
					createDir(remoteFile, sftp);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage(),e);
		            throw e;
				}

				this.sftp.put(new FileInputStream(file), file.getName());
				logger.debug("已经上传本地文件： " + localFile);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
		    logger.error(e.getMessage(),e);
            throw e;
		} catch (SftpException e) {
			// TODO Auto-generated catch block
		    logger.error(e.getMessage(),e);
            throw e;
		} catch(Exception e){
		    logger.error(e.getMessage(),e);
            throw e;
		}

	}

	/**
	 * create Directory
	 * 
	 * @param filepath
	 * @param sftp
	 * @throws Exception 
	 */
	private void createDir(String filepath, ChannelSftp sftp) throws Exception {
	    try{
    		filepath = filepath.replaceAll("\\\\", "/");
    		boolean bcreated = false;
    		boolean bparent = false;
    		// logger.debug(">>>>>>>>>filepath:"+filepath);
    		if (filepath == null) {
    			return;
    		}
    		File file = new File(filepath);
    		String ppath = file.getParent().replaceAll("\\\\", "/");
    		
    		try {
//    			logger.info("ppath>>>"+ppath+"this.sftp>>>"+this.sftp);
    			this.sftp.cd(ppath);
    			bparent = true;
    		} catch (SftpException e1) {
    			bparent = false;
    		}
    		try {
    			if (bparent) {
    				try {
    					this.sftp.cd(filepath);
//    					logger.debug("跳转文件夹成功：" + filepath);
    					bcreated = true;
    				} catch (Exception e) {
    					bcreated = false;
    					logger.debug("跳转文件夹异常：" + filepath);
    				}
    				if (!bcreated) {
    					this.sftp.mkdir(filepath);
    					bcreated = true;
    					logger.debug("创建文件夹：" + filepath);
    					this.sftp.cd(filepath);
    					logger.debug("创建后跳转文件夹成功：" + filepath);
    				}
    				
    				return;
    			} else {
    				createDir(ppath, sftp);
    				this.sftp.cd(ppath);
    				this.sftp.mkdir(filepath);
    			}
    		} catch (SftpException e) {
    			logger.error("创建目录失败 :" + filepath);
    			e.printStackTrace();
    			throw e;
    		}
    
    		try {
    			this.sftp.cd(filepath);
    		} catch (SftpException e) {
    			e.printStackTrace();
    			logger.error("不能跳转目录 :" + filepath);
    			throw e;
    		}
	     }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw e;
        }

	}

	/**
	 * get all the files need to be upload or download
	 * 
	 * @param file
	 * @return
	 */
	private List<String> getFileEntryList(String file) {
		ArrayList<String> fileList = new ArrayList<String>();
		InputStream in = null;
		try {

			in = new FileInputStream(file);
			InputStreamReader inreader = new InputStreamReader(in);

			LineNumberReader linreader = new LineNumberReader(inreader);
			String filepath = linreader.readLine();
			while (filepath != null) {
				fileList.add(filepath);
				filepath = linreader.readLine();
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (in != null) {
				in = null;
			}
		}

		return fileList;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the sftp
	 */
	public ChannelSftp getSftp() {
		return sftp;
	}

	/**
	 * @param sftp
	 *            the sftp to set
	 */
	public void setSftp(ChannelSftp sftp) {
		this.sftp = sftp;
	}

	private static void listFile(File path, List fileList) {
		File[] fs;
		if (path.isFile()) {
			fs = new File[] { path };
		} else {
			fs = path.listFiles();
		}
		if (fs != null)
			for (int i = 0; i < fs.length; i++) {
				fileList.add(fs[i].getAbsolutePath());
				if (fs[i].isDirectory()) {
					listFile(fs[i], fileList);
				}
			}
	}

	public static void main(String[] args) throws Exception {
    String base_ip= "192.168.0.81";
    String base_user= "root";
    String base_password= "kunteng888";
		 SftpUtil sftp = new SftpUtil(base_ip,22,base_user,base_password,"");
		 sftp.connect();
		 
		 /*****************上传sftp********************/
//		 String localFile="d:/File/K_SC_15100000_20160104.dat";
		String localFile="G:\\坤腾\\超汇VIPLog接口\\jftp.jar";
		 String remoteFileName="/home/yuan/test/";
		 sftp.upload(localFile, remoteFileName);
		 /*****************下载sftp********************/
//		 String remotePath="/home/was/payment/file/shopFile/collect/20160527";
//		 String remoteFilename = "K_SC_15100000_20160104.dat";
//		 String localFileName="D:/File/K_SC_15100000_20160104.dat";
//		 sftp.download(remotePath, remoteFilename, localFileName);
		 sftp.disconnect(); 
	}
}
