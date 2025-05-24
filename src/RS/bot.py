from telegram import Update
from telegram.ext import (
    ApplicationBuilder,
    CommandHandler,
    MessageHandler,
    ContextTypes,
    filters,
)

TOKEN = '7826448692:AAG6pmrrlVZqtcUBvbyDLf7g2HCC4QUMKrc'

async def start(update: Update, context: ContextTypes.DEFAULT_TYPE):
    if update.effective_chat.type != "private":
        return
    await update.message.reply_text("Welcome! Use /menu, /price, or /location.")

async def menu(update: Update, context: ContextTypes.DEFAULT_TYPE):
    if update.effective_chat.type != "private":
        return
    await update.message.reply_text("🍽 Today's Menu:\n- Pasta\n- Pizza\n- Salad")

async def price(update: Update, context: ContextTypes.DEFAULT_TYPE):
    if update.effective_chat.type != "private":
        return
    await update.message.reply_text("💲 Prices:\n- Pasta: $12\n- Pizza: $15\n- Salad: $8")

async def location(update: Update, context: ContextTypes.DEFAULT_TYPE):
    if update.effective_chat.type != "private":
        return
    await update.message.reply_text("📍 We are located at: 123 Food Street, Addis Ababa.")

async def handle_message(update: Update, context: ContextTypes.DEFAULT_TYPE):
    if update.effective_chat.type != "private":
        return  

    question = update.message.text
    answer = f"You asked: {question}. Here's your answer from the bot!"
    await update.message.reply_text(answer)

if __name__ == '__main__':
    app = ApplicationBuilder().token(TOKEN).build()

    app.add_handler(CommandHandler("start", start))
    app.add_handler(CommandHandler("menu", menu))
    app.add_handler(CommandHandler("price", price))
    app.add_handler(CommandHandler("location", location))
    app.add_handler(MessageHandler(filters.TEXT & ~filters.COMMAND, handle_message))

    app.run_polling()
