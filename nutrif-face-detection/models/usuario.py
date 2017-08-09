from common.database import db
from werkzeug.security import generate_password_hash, check_password_hash


class UsuarioModel(db.Model):
    __tablename__ = 'usuario'

    id = db.Column(db.Integer, primary_key=True)
    login = db.Column(db.String(40))
    senha = db.Column(db.String(255))

    def __init__(self, login, senha):
        self.login = login
        self.set_senha(senha)

    def set_senha(self, senha):
        self.senha = generate_password_hash(senha)

    def verificar_senha(self, value):
        return check_password_hash(self.senha, value)
