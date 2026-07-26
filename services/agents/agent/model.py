from dotenv import load_dotenv
from langchain_google_genai import ChatGoogleGenerativeAI

load_dotenv()

gemini_model = ChatGoogleGenerativeAI(
    model="gemini-2.5-flash",
    temperature=0,  # Gemini 3.0+ defaults to 1.0
)
