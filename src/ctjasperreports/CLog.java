///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package ctjasperreports;
//
///**
// *
// * @author Aldo Fernando Vilardy Roa
// */
/////  <summary>
/////  Manager class to log file
/////  </summary>
//public class CLog {
//
//    public CLog(String FileName) {
//        //  
//        //  TODO: Add constructor logic here
//        // 
//        strFileName = FileName;
//        // fileInfo = new FileInfo(string.Format("{0}.log",strFileName));
//    }
//
//    CLog() {
//        this.WriteLog(0, "**************  Close Log ****************");
//    }
//
//    ///  <summary>
//    ///  Sets debug level
//    ///  </summary>
//    public int DebugLevel = 3;
//
//    ///  <summary>
//    ///  Sets the Log FileName
//    ///  </summary>
//    public String strFileName;
//
//    public String strAppName = "unnamed";
//
//    ///  <summary>
//    ///  Set Maximum file saze in Kilobytes
//    ///  </summary>
//    public int MaxSize = -1;
//
//    ///  <summary>
//    ///  Configures if log files must be truncates when maximum size is reached, otherwise will append a new file
//    ///  </summary>
//    public boolean Truncate = false;
//
//    ///  <summary>
//    ///  File object
//    ///  </summary>
//    public FileInfo fileInfo = null;
//
//    public int FileCounter;
//
//    public const int COMMENT = 0;
//
//    public const int WARNING = 1;
//
//    public const int ERROR = 2;
//
//    public const int CRITICAL = 3;
//
//    ///  <summary>
//    ///  Starts the log file
//    ///  </summary>
//    public final boolean OpenLog() {
//        try {
//            String strDateDir = DateTime.Now.ToString("yyyy_MM_dd");
//            System.IO.DirectoryInfo LogsDir = new DirectoryInfo(string.Format("logs", strDateDir));
//            System.IO.DirectoryInfo LogsAppDir = new DirectoryInfo(string.Format("logs/{0}", this.strAppName));
//            System.IO.DirectoryInfo DateDir = new DirectoryInfo(string.Format("logs/{0}/{1}", this.strAppName, strDateDir));
//            if (!LogsDir.Exists) {
//                LogsDir.Create();
//                LogsAppDir.Create();
//                DateDir.Create();
//            } else if (!LogsAppDir.Exists) {
//                LogsAppDir.Create();
//                DateDir.Create();
//            } else if (!DateDir.Exists) {
//                DateDir.Create();
//            }
//
//            this.fileInfo = new FileInfo(string.Format("logs/{2}/{0}/{1}.log", strDateDir, this.strFileName, this.strAppName));
//            if (!this.fileInfo.Exists) {
//                FileStream File = this.fileInfo.Create();
//                File.Close();
//                // return true;
//            }
//
//        } catch (System.Exception ) {
//            return false;
//        }
//
//        return true;
//        // Letf intentionally to not delete the file
//        // else 
//        // {
//        //     //fileInfo.Delete();
//        //     FileStream File = fileInfo.Create();
//        //     File.Close();
//        //     return false;
//        // }
//    }
//
//    ///  <summary>
//    ///  Finishes the log file
//    ///  </summary>
//    public final void CloseLog() {
//        return;
//    }
//
//    ///  <summary>
//    ///  Writes a record to the logfile
//    ///  </summary>
//    public final void WriteLog(int LogLevel, string Description) {
//        try {
//            string strDateDir = DateTime.Now.ToString("yyyy_MM_dd");
//            if ((this.fileInfo != null)) {
//                // Check if directory exist (a new day)
//                DirectoryInfo dirInfo = new DirectoryInfo(string.Format("logs/{1}/{0}", strDateDir, this.strAppName));
//                if (!dirInfo.Exists) {
//                    dirInfo.Create();
//                    this.fileInfo = new FileInfo(string.Format("logs/{2}/{0}/{1}.log", strDateDir, this.strFileName, this.strAppName));
//                    FileStream File = this.fileInfo.Create();
//                    File.Close();
//                    this.FileCounter = 0;
//                } else {
//                    // Check the file exists
//                    FileInfo Info = new FileInfo(string.Format("logs/{2}/{0}/{1}.log", strDateDir, this.strFileName, this.strAppName));
//                    if (!Info.Exists) {
//                        this.fileInfo = new FileInfo(string.Format("logs/{2}/{0}/{1}.log", strDateDir, this.strFileName, this.strAppName));
//                        FileStream File = this.fileInfo.Create();
//                        File.Close();
//                    } else {
//                        this.fileInfo.Refresh();
//                    }
//
//                }
//
//                // lock(this)
//                typeof(CLog);
//                // TODO: lock is not supported at this time
//                if ((this.MaxSize > 0)) {
//                    // Manipulate file size truncation and deletion
//                    if ((this.fileInfo.Length
//                            > (this.MaxSize * 1024))) {
//                        // Maximum size has been reached
//                        if (this.Truncate) {
//                            // Delete file and create it again before writing
//                            this.fileInfo.Delete();
//                            // fileInfo = new FileInfo(string.Format("{0}.log", strFileName));
//                            this.fileInfo = new FileInfo(string.Format("logs/{2}/{0}/{1}.log", strDateDir, this.strFileName, this.strAppName));
//                            FileStream File = this.fileInfo.Create();
//                            File.Close();
//                        } else {
//                            // Create a new log file based on the FileCounter
//                            this.FileCounter++;
//                            this.fileInfo.CopyTo(string.Format("logs/{3}/{0}/{1}_{2}.log", strDateDir, this.strFileName, this.FileCounter.ToString(), this.strAppName), true);
//                            this.fileInfo.Delete();
//                            // fileInfo = new FileInfo(string.Format("{0}{1}.log", strFileName, FileCounter));
//                            this.fileInfo = new FileInfo(string.Format("logs/{2}/{0}/{1}.log", strDateDir, this.strFileName, this.strAppName));
//                            FileStream File = this.fileInfo.Create();
//                            File.Close();
//                        }
//
//                    }
//
//                }
//
//                // No need to file manipulation
//                StreamWriter Writer = this.fileInfo.AppendText();
//                string strLevel;
//                switch (LogLevel) {
//                    case COMMENT:
//                        strLevel = "(COMMENT) ";
//                        break;
//                    case WARNING:
//                        strLevel = "(WARNING) ";
//                        break;
//                    case ERROR:
//                        strLevel = "(ERROR) ";
//                        break;
//                    case CRITICAL:
//                        strLevel = "(CRITICAL) ";
//                        break;
//                    default:
//                        strLevel = "(UNKNOWN) ";
//                        break;
//                }
//                Writer.WriteLine("{0} {1}: {2}", DateTime.Now.ToString("dd/MM/yy HH:mm:ss.fff"), strLevel, Description);
//                Writer.Close();
//            }
//
//        } catch (System.Exception ) {
//
//        }
//
//    }
//}
