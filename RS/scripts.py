import random
import names

import firebase_admin
from firebase_admin import credentials, firestore

cred = credentials.Certificate("hallowed-welder-297014-042131c68dd6.json")
firebase_admin.initialize_app(cred)

db = firestore.client()

all_hobbies = ["Football", "Basketball", "Tennis", "Baseball", "Hip-Hop", "Pop", "Electro", "K-Pop",
               "Foreign Language Study", "Reading", "Blogging", "Writing", "Drawing", "Painting", "Photography",
               "Scrapbooking"]


def add_users(nb_users):
    for i in range(nb_users):
        interests = random.sample(all_hobbies, 3)
        print(interests)
        user_points = get_user_points(interests)
        user = {
            u'firstName': names.get_first_name(),
            u'lastName': names.get_last_name(),
            u'interests': interests,
            u'points': get_user_points(interests)
        }
        if user_points["Creative"] >= 2:
            db.collection(u'totallySpies').document("creative_users").collection('users').add(user)
        elif user_points["Enrichment"] >= 2:
            db.collection(u'totallySpies').document("enrichment_users").collection('users').add(user)
        elif user_points["Music"] >= 2:
            db.collection(u'totallySpies').document("music_users").collection('users').add(user)
        elif user_points["Sport"] >= 2:
            db.collection(u'totallySpies').document("sport_users").collection('users').add(user)
        else:
            db.collection(u'totallySpies').document("balanced_users").collection('users').add(user)

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

def get_user(document_name):
    all_groups = ["C", "I", "P", "R"]
    all_users_in_group = None

    for i in all_groups:
        doc_ref = db.collection(u'totallySpies').document(i).collection('users').document(document_name)
        doc = doc_ref.get()
        if doc.exists:
            print(doc.to_dict())
            all_users_in_group = db.collection('totallySpies').document(i).collection('users').stream()
            break
    all_users = []
    for users in all_users_in_group:
        all_users.append({k: v for k, v in users.to_dict().items() if v})
    return all_users

def parse_interest(document_name):
    result = db.collection('totallySpies').document(document_name).get()

    if result.exists:
        parsed_dict = {k: v for k, v in result.to_dict().items() if v}
        return parsed_dict


def find_right_group(user_interests):
    interest_group_c = parse_interest('Creative')
    interest_group_i = parse_interest('Enrichment')
    interest_group_p = parse_interest('Musique')
    interest_group_r = parse_interest('Sport')
    print(interest_group_c)

    groups_dict = {
        "I": set(interest_group_i),
        "P": set(interest_group_p),
        "R": set(interest_group_r),
        "C": set(interest_group_c)
    }
    set_user = set(user_interests)
    right_group = {
        "group": [],
        "value": []
    }

    for key, value in groups_dict.items():
        if not right_group['group']:
            right_group['group'].append(key)
            right_group['value'].append(value.intersection(set_user))
        elif len(right_group['value'][0]) < len(value.intersection(set_user)):
            right_group['group'].clear()
            right_group['value'].clear()
            right_group['group'].append(key)
            right_group['value'].append(value.intersection(set_user))
        elif len(right_group['value'][0]) == len(value.intersection(set_user)):
            right_group['group'].append(key)
            right_group['value'].append(value.intersection(set_user))

    return right_group['group']


def main():
    # add_users(2)
    user_interests = ["interest33", "interest12", "interest45", "interest153", "interest198", "interest109", "interest178",
                     "interest200", "interest202", "interest217"]

    user_interest_form = {interest: "1" for interest in user_interests}
    user_interestsA = {  # VARIABLE FOR TESTING PURPOSES
        "interest1": "1",
        "interest207": "1",
        "interest134": "1",
        "interest59": "1",
        "interest64": "1",
        "interest137": "1",
        "interest23": "1",
        "interest9": "1",
        "interest14": "1",
        "interest89": "1",
    }
    # user_interestsB = {  # VARIABLE FOR TESTING PURPOSES
    #     "interest33": "1",
    #     "interest12": "1",
    #     "interest45": "1",
    #     "interest153": "1",
    #     "interest198": "1",
    #     "interest109": "1",
    #     "interest178": "1",
    #     "interest200": "1",
    #     "interest202": "1",
    #     "interest217": "1",
    # }

    #find_right_group(user_interest_form)
    add_users(100)
    #find_right_group("ok")

if __name__ == "__main__":
    main()
