set JX_HOME=c:\JDX
javac -source 1.8 -target 1.8 -d .\bin -cp .;%JX_HOME%\libs\jxclasses.jar;%JX_HOME%\external_libs\json-20160212.jar @sources.txt