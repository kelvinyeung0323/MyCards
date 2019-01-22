package com.taurus.mycards.nfc.IDCard;


class IDCardDataFrame {

  //  preamble 前导码，前3个字节用于帧同步，后两个表示一帧的起始位置
  private char[] preamble = new char[]{0xAA,0xAA,0xAA,0x96,0x69};
  // 通信中的有效数据长度，用两个字节表示
  private char len1; //本次传输的数据长度高字节
  private char len2; //本次传输的数据长度低字节

  private char cmd;
  private char para;
  private char[] data; //本次传输的数据，可以为空

  private char sw1; //身份证阅读器转发的身份证卡状态
  private char sw2; //身份证阅读器转发的身份证卡状态
  private char sw3; //身份证阅读器自身返回状态
  private char chkSum;//除前导码和校验码和之外的所有数据字节的按位异或





  public char[] getLen(){
    //TODO:
    //输入数据长度=cmd+para + data + chkSum
    //输出数据长充=sw1 + sw2 + sw3 + data + chkSum
  }



}