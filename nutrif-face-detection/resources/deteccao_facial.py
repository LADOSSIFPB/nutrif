from flask import g
from flask_restful import Resource, marshal_with, abort
from common.auth import auth
from models.aluno import Aluno, aluno_json

# GET /detectaface/
class DeteccaoFacialResource(Resource):

    @marshal_with(aluno_json)
    def get(self, matricula):
        aluno = Aluno(matricula, "base64")
        return (aluno, 200)
