#include <stdio.h>
#include <stdlib.h>

struct Node
{
    int degree;
    int coefficient;
    struct Node *next;
    struct Node *prev;
};

Node *createNode(int d, int c)
{
    Node *newNode = (Node *)malloc(sizeof(Node));

    newNode->degree = d;
    newNode->coefficient = c;

    newNode->next = NULL;
    newNode->prev = NULL;

    return newNode;
}

void appendNode(Node **head, Node *newNode)
{
    Node *curr = *head;

    if (*head == NULL)
    {
        *head = newNode;
    }
    else if (curr->degree > newNode->degree)
    {
        newNode->next = curr;
        *head = newNode;
    }
    else
    {
        while (curr->next != NULL && curr->next->degree < newNode->degree)
        {
            curr = curr->next;
        }

        if (curr->degree == newNode->degree)
        {
            curr->coefficient += newNode->coefficient;
            delete (newNode);
        }

        else
        {
            newNode->next = curr->next;
            curr->next = newNode;
        }
    }
}

Node *inputpoly(void)
{
    int d, c;

    Node *poly = NULL;
    Node *newNode = NULL;

    do
    {
        printf("Input (degree) (coefficient): ");
        scanf("%d %d", &d, &c);

        if ((d >= 0) && (c >= 0))
        {
            if (c == 0)
            {
                d = 0;
            }
            newNode = createNode(d, c);
            appendNode(&poly, newNode);
        }
        else if (d * c < 0)
        {
            printf("Please enter the numbers of the same sign!\n");
        }
        else
        {
            printf("Done!!\n");
            return poly;
        }
    } while (true);
}

void printNode(Node *inp)
{
    Node *curr = inp;

    while (curr != NULL)
    {
        if (curr->degree == 0)
        {
            printf("%d", curr->coefficient);

            if (curr->next == NULL)
            {
                curr = curr->next;
            }
            else
            {
                printf("+");
                curr = curr->next;
            }
        }
        else
        {
            if (curr->coefficient == 0)
            {
                if (curr->next == NULL)
                {
                    curr = curr->next;
                }
                else
                {
                    printf("+");
                    curr = curr->next;
                }
            }
            else
            {
                printf("%dx^%d", curr->coefficient, curr->degree);
                if (curr->next == NULL)
                {
                    curr = curr->next;
                }
                else
                {
                    printf("+");
                    curr = curr->next;
                }
            }
        }
    }
    printf("\n");
}

Node *multiply(Node *a, Node *b)
{
    Node *curr1 = a;
    Node *curr2 = b;
    Node *newNode = NULL;
    Node *result = NULL;

    while (curr1 != NULL)
    {
        while (curr2 != NULL)
        {
            int c, d;

            c = curr1->coefficient * curr2->coefficient;
            d = curr1->degree + curr2->degree;

            newNode = createNode(d, c);
            appendNode(&result, newNode);
            curr2 = curr2->next;
        }
        curr2 = b;
        curr1 = curr1->next;
    }
    return (result);
}

int main(void)
{
    printf("-------------------test-------------------\n");
    Node *a = inputpoly();
    printNode(a);
    Node *b = inputpoly();
    printNode(b);
    printf("Result: ");
    printNode(multiply(a, b));

    free(a);
    free(b);

    return 0;
}