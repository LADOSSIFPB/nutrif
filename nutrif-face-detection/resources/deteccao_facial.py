from flask import g
from flask_restful import Resource, marshal_with, abort
from common.auth import auth
from models.aluno import Aluno, aluno_json
import base64

# GET /detectaface/
class DeteccaoFacialResource(Resource):

    @marshal_with(aluno_json)
    def get(self, matricula):
        with open("ifpb.png", "rb") as f:
            data = f.read()
            foto = base64.b64encode(data)
        aluno = Aluno(matricula, foto)
        return (aluno, 200)
