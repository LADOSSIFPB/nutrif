from flask_restful import fields

aluno_json = {
    'matricula': fields.String,
    'foto': fields.String
}

class Aluno():
    def __init__(self, matricula, foto):
        self.matricula = matricula
        self.foto = foto