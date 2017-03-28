# Import the os module, for the os.walk function
import os
import shutil

"""
	
"""
def parser():
	# Set the directory you want to start from
	rootDir = str(input("Informe o diretório de origem:\n"))
	
	destDir = str(input("Informe o diretório de destino:\n"))
	
	for dirName, subdirList, fileList in os.walk(rootDir):
		print('Diretório encontrado: %s' % dirName)
		dirMatr = dirName.find("-")
		if dirMatr != -1:
			matricula = dirName[dirMatr + 2:]		
			dirCopy = destDir + "\\" + matricula
			shutil.copytree(dirName, dirCopy)
			for fname in fileList:
				print('\t%s' % fname)
		else:
			
			print("Não tem matrícula.")

def main(argsv=""):
	parser()
	
if __name__ == "__main__":
	main()
