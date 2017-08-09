import os

# preparar configuração do banco
# pegar as variaveis de ambiente

usuario = 'root'
senha = 'ifpb'

usuario_ocs = 'root'
senha_ocs = 'ifpb'

# uri
uri_mysql = 'mysql+mysqlconnector://{}:{}@localhost/{}'.format(
    usuario,
    senha,
    'checkin')

uri_ocs = 'mysql+mysqlconnector://{}:{}@localhost/{}'.format(
    usuario_ocs,
    senha_ocs,
    'ocs')

SQLALCHEMY_DATABASE_URI = uri_mysql
SQLALCHEMY_TRACK_MODIFICATIONS = False
SQLALCHEMY_BINDS = {
    'ocs': uri_ocs
}
