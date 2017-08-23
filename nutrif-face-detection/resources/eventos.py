from flask import g
from flask_restful import Resource, marshal_with, abort
from common.auth import auth
from models.evento import EventoModel, evento_campos
from models.aluno import Aluno, aluno_json

# GET /eventos/
class EventosResource(Resource):
    @marshal_with(aluno_json)
    def get(self):
        aluno = Aluno("2222222")
        return aluno, 200

# GET /eventos/<id>
class EventoResource(Resource):
    @auth.login_required
    @marshal_with(evento_campos)
    def get(self, evento_id):
        evento = EventoModel.query.filter_by(id=evento_id).first()
        if evento is None:
            abort(404, message='Evento {} nao existe'.format(evento_id))
        return evento, 200
