<?php

/**
* Error logging static class 
*/
class Logger
{
	private static $fileName = 'log.txt';

	public static function log($logString)
	{
		$string = date("Y-m-d H:i:s").': ' .$logString ."\r\n";

		$fileHandle = fopen(self::$fileName, 'a+');

		fwrite($fileHandle, $string);
		fclose($fileHandle);
	}
}
?>