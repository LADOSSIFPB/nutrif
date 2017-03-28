import os
import shutil
import glob

def parser():
    rootDir = str(input("Informe o diretório de origem:\n"))
    destDir = str(input("Informe o diretório de destino:\n"))

    for dirName, subdirList, fileList in os.walk(rootDir):
        print("Diretorio encontrado: %s" % dirName)
        has_image = len(glob.glob(dirName + "\*.jpg"))
        if has_image != 0:
            dirMatr = dirName.find("-")
            if dirMatr != -1:
                if dirName[dirMatr + 1] == " ":
                    matricula = dirName[dirMatr + 2:]
                elif dirName[dirMatr + 1] != " ":
                    matricula = dirName[dirMatr + 1:]
                dirCopy = destDir + "\\" + matricula
                shutil.copytree(dirName, dirCopy)
                for fname in fileList:
                    print("\t%s" % fname)
            else:
                print("Não tem matrícula")

def main():
    parser()

if __name__ == "__main__":
    main()
