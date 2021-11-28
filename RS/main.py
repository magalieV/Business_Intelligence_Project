import firebase_admin
from firebase_admin import credentials, firestore

cred = credentials.Certificate("hallowed-welder-297014-042131c68dd6.json")
firebase_admin.initialize_app(cred)

db=firestore.client()


def parse_interest(document_name):
    result = db.collection('totallySpies').document(document_name).get()

    if result.exists:
        parsed_dict = {k: v for k, v in result.to_dict().items() if v}
        print(parsed_dict)


def main():
    parse_interest('C')


if __name__ == "__main__":
    main()

