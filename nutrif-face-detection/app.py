from flask import Flask, Blueprint
from flask_restful import Api
from resources.deteccao_facial import *
from flask_httpauth import HTTPBasicAuth

app = Flask(__name__)
app.config['DEBUG'] = True
api_bp = Blueprint('api', __name__)
api = Api(api_bp)

api.add_resource(DeteccaoFacialResource, '/detectarface/<string:matricula>')

app.register_blueprint(api_bp, url_prefix='/api')

auth = HTTPBasicAuth()

if __name__ == '__main__':
    app.run()
