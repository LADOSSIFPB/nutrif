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
                matricula = dirName.split("-")
                matricula = matricula[-1]
                matricula = matricula.strip()
                dirCopy = destDir + "\\" + matricula
                shutil.copytree(dirName, dirCopy)
                for fname in fileList:
                    print("\t%s" % fname)
            else:
                nomeAluno = dirName.split("\\")
                nomeAluno = nomeAluno[-1].strip()

                arquivo = open(destDir + "\\" + "alunos_sem_matricula.txt", "a")
                arquivo.write(nomeAluno + "\n")
                arquivo.close()

                print(nomeAluno, "não tem matrícula")

def main():
    parser()

if __name__ == "__main__":
    main()
