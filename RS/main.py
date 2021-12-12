from flask import Flask, request, jsonify
import firebase_admin
from firebase_admin import credentials, firestore
import random

cred = credentials.Certificate("hallowed-welder-297014-042131c68dd6.json")
firebase_admin.initialize_app(cred)

db = firestore.client()

app = Flask(__name__)

Sport = ["Football", "Basketball", "Tennis", "Baseball"]
Musique = ["Hip-Hop", "Pop", "Electro", "K-Pop"]
Enrichment = ["Foreign Language Study", "Reading", "Blogging", "Writing"]
Creative = ["Drawing", "Painting", "Photography", "Scrapbooking"]

@app.route('/')
def index():
     return "TotallySpies is so Fun hihi"


@app.route('/user_info', methods=['PUT'])
def update_user_info():
    user = {
        'first_name': request.json['first_name'],
        'last_name': request.json['last_name'],
        'interests': request.json['interests']
    }

    user_points = get_user_points(user['interests'])

    doc_ref = db.collection(u'totallySpies').document("all_users").collection('users').document(request.args.get("user_id"))
    doc_ref.update({
        db.field_path(u'first name'): user['first_name'],
        db.field_path(u'last name'): user['last_name'],
        u'interests': user['interests'],
        u'points': user_points
    })
    return jsonify(doc_ref.id)


@app.route('/user_info', methods=['GET'])
def get_user_info():
    document_name = request.args.get("user_id")

    doc_ref = db.collection(u'totallySpies').document('all_users').collection('users').document(document_name)
    doc = doc_ref.get()
    if doc.exists:
        return jsonify(doc.to_dict())


@app.route('/matched_users', methods=['GET'])
def get_user_possible_matchable_users():
    all_users_in_group = None
    document_name = request.args.get("user_id")

    doc_ref = db.collection(u'totallySpies').document("all_users").collection('users').document(document_name)
    doc = doc_ref.get()
    if doc.exists:
        all_users_in_group = db.collection('totallySpies').document("all_users").collection('users').stream()

    all_users = []
    for users in all_users_in_group:
        all_users.append({k: v for k, v in users.to_dict().items() if v})

    user_points = doc.to_dict()["points"]
    algo_list = []
    for key, value in user_points.items():
        for i in range(value):
            algo_list.append(key)

    final_list = []
    while len(final_list) != 5:
        user = random.choice(all_users)
        for key, value in user["points"].items():
            if key == random.choice(algo_list) and value > 0:
                final_list.append(user)
    return jsonify(final_list)


@app.route('/users', methods=['POST'])
def add_user():
    user = {
        'first_name': request.json['first_name'],
        'last_name': request.json['last_name'],
        'interests': request.json['interests']
    }

    user_points = get_user_points(user['interests'])

    data = {
        u'first name': user['first_name'],
        u'last name': user['last_name'],
        u'interests': user['interests'],
        u'points': user_points
    }

    doc_ref = db.collection(u'totallySpies').document("all_users").collection('users').document()
    doc_ref.set(data)
    return jsonify(doc_ref.id)


def get_user_points(user_interests):
    creative_group = parse_interest('Creative')['Hobbies']
    enrichment_group = parse_interest('Enrichment')['Hobbies']
    musique_group = parse_interest('Musique')['Hobbies']
    sport_group = parse_interest('Sport')['Hobbies']

    creative_points = 0
    enrichment_points = 0
    musique_points = 0
    sport_points = 0

    all_groups = {
        "Creative": creative_group,
        "Enrichment": enrichment_group,
        "Music": musique_group,
        "Sport": sport_group
    }

    for key, value in all_groups.items():
        for hobby in value:
            for user_interest in user_interests:
                if user_interest == hobby and key == "Creative":
                    creative_points += 1
                elif user_interest == hobby and key == "Enrichment":
                    enrichment_points += 1
                elif user_interest == hobby and key == "Music":
                    musique_points += 1
                elif user_interest == hobby and key == "Sport":
                    sport_points += 1

    return {
        "Creative": creative_points,
        "Enrichment": enrichment_points,
        "Music": musique_points,
        "Sport": sport_points
    }


def parse_interest(document_name):
    result = db.collection('totallySpies').document(document_name).get()

    if result.exists:
        parsed_dict = {k: v for k, v in result.to_dict().items() if v}
        return parsed_dict


if __name__ == '__main__':
    app.run(debug=True, port=8080)