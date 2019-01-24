import glob, re, sys
from importlib import import_module


usageMsg = """
usage:
python checkFiles.py
"""

def usage():
    print(usageMsg)
    sys.exit()

def readFile(fileName):
    """Read a file, returning its contents as a single string.

    Args:
    
        fileName (str): The name of the file to be read.

    Returns: 

        str: The contents of the file.
    """

    fileContents = ''
    with open(fileName) as inputFile:
        fileContents = inputFile.read()
    return fileContents

def checkHasMain(java_files):
    print('Checking that every Java file has a main() method...')
    filesToCheck = java_files - set([])
    mainStr = 'public static void main'
    for filename in sorted(filesToCheck):
        progString = readFile(filename)
        if mainStr not in progString:
            print('Warning: file', filename, 'does not appear to have a main() method')
        sys.stdout.flush()
    print('   ... finished checking main() methods')
    sys.stdout.flush()
    
def main():
    prefix = 'src\\wcbc\\'
    all_java_files = set(glob.glob(prefix + '*.java'))

    dont_test_files = set(['WcbcException.java',
                           'WaitAllTerminated.java',
                           'TwoTDCM.java', 
                           'Edge.java',
                           'Graph.java',
                           'NDTuringMachine.java',
                           'Nfa.java',
                           'NonDetSolution.java',
                           'Path.java',
                           'PathAndLen.java',
                           'Siso.java',
                           'Siso2.java',
                           'Siso3.java',
                           'TrueInPeano.java',
                           'TwoTDCM.java',
                           'WaitAllTerminated.java',
                           'WcbcException.java',
    ])
    
    dont_test_files = set([prefix + f for f in dont_test_files])
    java_files_to_test = all_java_files - dont_test_files

    # print('all_java_files:', all_java_files)
    # print('dont_test_files:', dont_test_files)
    # print('len(java_files_to_test)', len(java_files_to_test))
    checkHasMain(java_files_to_test)    
    
if __name__ == '__main__':
    main()
    
