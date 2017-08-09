from common.database import db
from flask_restful import fields

evento_campos = {
    'id': fields.Integer,
    'nome': fields.String,
}

class EventoModel(db.Model):
    __tablename__ = 'evento'

    id = db.Column(db.Integer, primary_key=True)
    nome = db.Column(db.String(150))
    data_inicio = db.Column(db.DateTime)
    data_fim = db.Column(db.DateTime)
    ocs_conferencia_id = db.Column(db.Integer)
    ocs_evento_id = db.Column(db.Integer)
    participantes = db.relationship("ParticipanteEventoModel",
                                    back_populates="evento")
