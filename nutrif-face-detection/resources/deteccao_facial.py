from flask import g
from flask_restful import Resource, marshal_with, abort
from common.auth import auth
from models.aluno import Aluno, aluno_json
from webcam import Deteccao

# GET /detectaface/
class DeteccaoFacialResource(Resource):

    @marshal_with(aluno_json)
    def get(self, matricula):
        d = Deteccao()
        foto = d.detectar()
        aluno = Aluno(matricula, foto)
        return (aluno, 200)