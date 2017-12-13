import requests
from flask_restful import Resource, marshal_with
from common.settings import uri_openface
from models.aluno import Aluno, aluno_json
from webcam import Deteccao

# GET /detectarface/
class DeteccaoFacialResource(Resource):

    @marshal_with(aluno_json)
    def get(self, matricula):
        d = Deteccao()
        foto = d.detectar()
        aluno = Aluno(matricula, foto)
        res = requests.post(uri_openface, json=aluno)
        return res.text, res.status_code