import firebase_admin
from firebase_admin import credentials, firestore

cred = credentials.Certificate("hallowed-welder-297014-042131c68dd6.json")
firebase_admin.initialize_app(cred)

db=firestore.client()


def parse_interest(document_name):
    result = db.collection('totallySpies').document(document_name).get()

    if result.exists:
        parsed_dict = {k: v for k, v in result.to_dict().items() if v}
        return parsed_dict


def find_right_group(interest_group_c, interest_group_r, interest_group_p, interest_group_i, user_interests):
    groups = [set(interest_group_i), set(interest_group_p), set(interest_group_r), set(interest_group_c)]
    set_user = set(user_interests)

    for group in groups:
        print(group.intersection(set_user))

def main():
    interest_group_c = parse_interest('C')
    interest_group_i = parse_interest('I')
    interest_group_p = parse_interest('P')
    interest_group_r = parse_interest('R')
    user_interests = {
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
    find_right_group(interest_group_c, interest_group_r, interest_group_p, interest_group_i, user_interests)


if __name__ == "__main__":
    main()

