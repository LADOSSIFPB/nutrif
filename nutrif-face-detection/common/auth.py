from flask import g, abort
from flask_httpauth import HTTPBasicAuth
from models.usuario import UsuarioModel
auth = HTTPBasicAuth()

@auth.verify_password
def verificar_senha(login, senha):
    g.usuario = UsuarioModel.query.filter_by(login=login).first()
    return g.usuario is not None and g.usuario.verificar_senha(senha)
