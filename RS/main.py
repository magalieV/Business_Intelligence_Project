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

# request.json["userId"]
@app.route('/user_info', methods=['POST'])
def update_user_info():
    document_name = request.json["userId"]

    user_group = get_user_group(document_name)
    db.collection(u'totallySpies').document(user_group).collection('users').document(document_name).delete()

    user = {
        'firstName': request.json['firstName'],
        'lastName': request.json['lastName'],
        'interests': request.json['interests']
    }

    user_points = get_user_points(user['interests'])

    data = {
        u'firstName': user['firstName'],
        u'lastName': user['lastName'],
        u'interests': user['interests'],
        u'points': user_points
    }
    if user_points["Creative"] >= 2:
        doc_ref = db.collection(u'totallySpies').document("creative_users").collection('users').document()
        doc_ref.set(data)
    elif user_points["Enrichment"] >= 2:
        doc_ref = db.collection(u'totallySpies').document("enrichment_users").collection('users').document()
        doc_ref.set(data)
    elif user_points["Music"] >= 2:
        doc_ref = db.collection(u'totallySpies').document("music_users").collection('users').document()
        doc_ref.set(data)
    elif user_points["Sport"] >= 2:
        doc_ref = db.collection(u'totallySpies').document("sport_users").collection('users').document()
        doc_ref.set(data)
    else:
        doc_ref = db.collection(u'totallySpies').document("balanced_users").collection('users').document()
        doc_ref.set(data)
    return jsonify(doc_ref.id)


@app.route('/user_info', methods=['GET'])
def get_user_info():
    document_name = request.args.get("user_id")

    user_group = get_user_group(document_name)

    doc_ref = db.collection(u'totallySpies').document(user_group).collection('users').document(document_name)
    doc = doc_ref.get()
    if doc.exists:
        return jsonify(doc.to_dict())


@app.route('/matched_users', methods=['GET'])
def get_user_possible_matchable_users():
    all_users_in_group = None
    document_name = request.args.get("user_id")

    user_group = get_user_group(document_name)

    doc_ref = db.collection(u'totallySpies').document(user_group).collection('users').document(document_name)
    doc = doc_ref.get()
    if doc.exists:
        all_users_in_group = db.collection('totallySpies').document(user_group).collection('users').stream()

    all_users = []
    for users in all_users_in_group:
        all_users.append({k: v for k, v in users.to_dict().items() if v})

    final_list = []
    while len(final_list) != 5:
        user = random.choice(all_users)
        final_list.append(user)
    return jsonify(final_list)


@app.route('/users', methods=['POST'])
def add_user():
    user = {
        'firstName': request.json['firstName'],
        'lastName': request.json['lastName'],
        'interests': request.json['interests']
    }

    user_points = get_user_points(user['interests'])

    data = {
        u'firstName': user['firstName'],
        u'lastName': user['lastName'],
        u'interests': user['interests'],
        u'points': user_points
    }
    if user_points["Creative"] >= 2:
        doc_ref = db.collection(u'totallySpies').document("creative_users").collection('users').document()
        doc_ref.set(data)
    elif user_points["Enrichment"] >= 2:
        doc_ref = db.collection(u'totallySpies').document("enrichment_users").collection('users').document()
        doc_ref.set(data)
    elif user_points["Music"] >= 2:
        doc_ref = db.collection(u'totallySpies').document("music_users").collection('users').document()
        doc_ref.set(data)
    elif user_points["Sport"] >= 2:
        doc_ref = db.collection(u'totallySpies').document("sport_users").collection('users').document()
        doc_ref.set(data)
    else:
        doc_ref = db.collection(u'totallySpies').document("balanced_users").collection('users').document()
        doc_ref.set(data)
    return jsonify(doc_ref.id)


def get_user_group(document_name):
    all_groups = ["creative_users", "enrichment_users", "sport_users", "music_users", "balanced_users"]
    all_users_in_group = None

    for group in all_groups:
        doc_ref = db.collection(u'totallySpies').document(group).collection('users').document(document_name)
        doc = doc_ref.get()
        if doc.exists:
            return group
            # return db.collection('totallySpies').document(i).collection('users').stream()


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