from flask import g
from flask_restful import Resource, marshal_with, abort
from common.auth import auth
from models.aluno import Aluno, aluno_json
import webcam
import base64
from time import sleep

# GET /detectaface/
class DeteccaoFacialResource(Resource):

    @marshal_with(aluno_json)
    def get(self, matricula):
        foto = webcam.main()
        aluno = Aluno(matricula, foto)
        return (aluno, 200)
